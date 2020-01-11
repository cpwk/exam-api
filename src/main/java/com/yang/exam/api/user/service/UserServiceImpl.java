package com.yang.exam.api.user.service;

import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import com.yang.exam.api.collect.service.CollectService;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.service.MistakesService;
import com.yang.exam.api.user.authority.UserSessionWrap;
import com.yang.exam.api.user.entity.OmsProfile;
import com.yang.exam.api.user.entity.UserError;
import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.qo.UserQo;
import com.yang.exam.api.user.qo.UserSessionQo;
import com.yang.exam.api.user.repository.UserRepository;
import com.yang.exam.api.user.repository.UserSessionRepository;
import com.yang.exam.api.usrPaper.service.UsrPaperService;
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.common.service.ICommonService;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.entity.Constants;
import com.yang.exam.commons.exception.RepositoryException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.ipseeker.IPSeekerUtil;
import com.yang.exam.commons.support.SupportService.SupportService;
import com.yang.exam.commons.support.entity.SupportError;
import com.yang.exam.commons.support.model.VCode;
import com.yang.exam.commons.utils.CollectionUtil;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

@Service
public class UserServiceImpl implements UserService, UserError, SupportError {

    private static final int USERNAME_LENGTH_MIN = 3;
    private static final int USERNAME_LENGTH_MAX = 10;


    @Value("${admin.salt}")
    private String salt;

    @Value("${admin.session-days}")
    private int sessionDays;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SupportService supportService;
    @Autowired
    private ICommonService commonService;
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private UsrPaperService usrPaperService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private MistakesService mistakesService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, User> userCache;

    @PostConstruct
    public void init() {
        userCache = kvCacheFactory.create(new CacheOptions("user", 1, Constants.CACHE_REDIS_EXPIRE),
                new RepositoryProvider<Integer, User>() {

                    @Override
                    public User findByKey(Integer key) throws RepositoryException {
                        return userRepository.findById(key).orElse(null);
                    }

                    @Override
                    public Map<Integer, User> findByKeys(Collection<Integer> ids) throws RepositoryException {
                        throw new UnsupportedOperationException("findByKeys");
                    }

                }, new BeanModelConverter<>(User.class));
    }

    @Override
    public UserSessionWrap signin(User user, VCode vCode, String ip) throws Exception {

        User exist = null;
        if (StringUtils.isEmail(user.getUsername())) {
            if ((exist = userRepository.findByEmail(user.getUsername())) == null) {
                throw new ServiceException(ERR_USER_USERNAME_INVALID);
            }
            if (exist.getStatus() == STATUS_HALT) {
                throw new ServiceException(ERR_USER_DISABLE);
            }
            VCode vc = supportService.getVcode(vCode.getKey());
            compare(vCode, vc, user.getUsername());
        } else if (StringUtils.isChinaMobile(user.getUsername())) {
            if ((exist = userRepository.findByMobile(user.getUsername())) == null) {
                throw new ServiceException(ERR_USER_USERNAME_INVALID);
            }
            if (exist.getStatus() == STATUS_HALT) {
                throw new ServiceException(ERR_USER_DISABLE);
            }
            VCode vc = supportService.getVcode(vCode.getKey());
            compare(vCode, vc, user.getUsername());
        } else {
            if (StringUtils.isEmpty(vCode.getCode())) {
                throw new ServiceException(ERR_ILLEGAL_ARGUMENT);
            }
            VCode vc = commonService.getValCode(vCode.getKey());
            if (vc.getCode().equals(vCode.getCode())) {
                if ((exist = userRepository.findByUsername(user.getUsername())) == null) {
                    throw new ServiceException(ERR_USER_USERNAME_INVALID);
                }
                if (exist.getStatus() == STATUS_HALT) {
                    throw new ServiceException(ERR_USER_DISABLE);
                }
                if (StringUtils.isEmpty(user.getPassword())) {
                    throw new ServiceException(ERR_PASSWORD_EMPTY);
                }
                if (!exist.getPassword().equals(StringUtils.encryptPassword(user.getPassword(), salt))) {
                    throw new ServiceException(ERR_USER_PASSWORD_INVALID);
                }
            } else {
                throw new ServiceException(ERROR_VCODE);
            }
        }
        UserSession session = createSession(exist, ip);
        return new UserSessionWrap(exist, session);
    }

    private UserSession createSession(User user, String ip) {
        UserSession userSession = new UserSession();
        userSession.setUserId(user.getId());
        userSession.setToken(StringUtils.randomString(salt, Constants.USER_TOKEN_LENGTH));
        Long now = System.currentTimeMillis();
        userSession.setSigninAt(now);
        userSession.setExpireAt(now + sessionDays * Constants.DAY_MILLIS);
        userSession.setIp(ip);
        userSession.setLocation(IPSeekerUtil.getFullLocation(ip));
        userSessionRepository.save(userSession);
        return userSession;
    }

    private void compare(VCode vCode, VCode vc, String username) {
        if (StringUtils.isEmpty(vCode.getCode()) || StringUtils.isNotEqual(username, vc.getAccount()) || StringUtils.isNotEqual(vCode.getCode(), vc.getCode())) {
            throw new ServiceException(ERROR_VCODE);
        }
    }

    @Override
    public UserSession findSessionByToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        return userSessionRepository.findByToken(token);
    }

    @Override
    public void signup(User user, VCode vCode) throws Exception {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getMobile()) || StringUtils.isEmpty(user.getPassword())) {
            throw new ServiceException(ERR_ILLEGAL_ARGUMENT);
        }
        if (user.getUsername().length() < USERNAME_LENGTH_MIN || user.getUsername().length() > USERNAME_LENGTH_MAX) {
            throw new ServiceException(ERR_USER_USERNAME);
        }
        if (!StringUtils.isChinaMobile(user.getMobile())) {
            throw new ServiceException(ERR_MOBILE_INVALID);
        }
        if (StringUtils.isEmpty(vCode.getCode())) {
            throw new ServiceException(ERR_VCODE_EMPTY);
        }
        VCode vc = supportService.getVcode(vCode.getKey());
        if (StringUtils.isNotEqual(user.getMobile(), vc.getAccount())) {
            throw new ServiceException(ERR_VCODE_EMPTY);
        }
        if (!vCode.getCode().equals(vc.getCode())) {
            throw new ServiceException(ERROR_VCODE);
        }
        if (userRepository.findByMobile(user.getMobile()) != null) {
            throw new ServiceException(ERROR_MOBILE_OCCUPY);
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ServiceException(ERR_USERNAME_EXISTENCE);
        }
        if (!StringUtils.checkPassword(user.getPassword())) {
            throw new ServiceException(ERR_USER_PASSWORD_LENGTH);
        }
        user.setStatus(STATUS_OK);
        user.setSignupAt(System.currentTimeMillis());
        user.setPassword(StringUtils.encryptPassword(user.getPassword(), salt));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(User user, VCode vCode) throws Exception {
        VCode vc = supportService.getVcode(vCode.getKey());
        if (StringUtils.isEmail(user.getUsername())) {
            if (userRepository.findByEmail(user.getUsername()) == null) {
                throw new ServiceException(ERR_USER_EMAIL_INVALID);
            }
            User exist = userRepository.findByEmail(user.getUsername());
            if (StringUtils.isNotEqual(user.getEmail(), vc.getAccount())
                    && StringUtils.isNotEqual(vCode.getCode(), vc.getCode())) {
                throw new ServiceException(ERROR_VCODE);
            }
            checkPassword(user, exist);
        } else if (StringUtils.isChinaMobile(user.getUsername())) {
            if (userRepository.findByMobile(user.getUsername()) == null) {
                throw new ServiceException(ERR_USER_EMAIL_INVALID);
            }
            User exist = userRepository.findByMobile(user.getUsername());
            if (StringUtils.isNotEqual(user.getMobile(), vc.getAccount())
                    && StringUtils.isNotEqual(vCode.getCode(), vc.getCode())) {
                throw new ServiceException(ERROR_VCODE);
            }
            checkPassword(user, exist);
        }

    }

    private void checkPassword(User user, User exist) {
        if (!StringUtils.checkPassword(user.getPassword())) {
            throw new ServiceException(ERR_USER_PASSWORD_LENGTH);
        }
        exist.setPassword(StringUtils.encryptPassword(user.getPassword(), salt));
        userRepository.save(exist);
    }

    @Override
    public Map modifyProfile(User user) throws Exception {
        Integer id = Contexts.requestUser().getId();
        User exist = getById(id);
        exist.setAvatar(user.getAvatar());
        exist.setUsername(user.getUsername());
        exist.setEmail(user.getEmail());
        userRepository.save(exist);
        Map<String, User> map = new HashMap<>();
        map.put("user", exist);
        userCache.remove(id);
        return map;
    }

    @Override
    public Map profile() throws Exception {
        Integer userId = Contexts.requestUser().getId();
        User user = user(userId, true);
        return CollectionUtil.arrayAsMap("user", user);
    }

    @Override
    public User user(int id, boolean init) {
        User user = user(id);
        if (init) {
        }
        return user;
    }

    private User user(int id) {
        return userCache.findByKey(id);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getById(Integer id) {
        User user = findById(id);
        if (user == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserSession userSession(String token) {
        return userSessionRepository.findByToken(token);
    }

    //oms
    @Override
    public Page<User> users(UserQo userQo) throws Exception {
        Page<User> users = userRepository.findAll(userQo);
        return users;
    }

    @Override
    public void status(Integer id) {
        User user = findById(id);
        if (user == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        if (user.getStatus().equals(STATUS_OK)) {
            user.setStatus(STATUS_HALT);
        } else {
            user.setStatus(STATUS_OK);
        }
        userRepository.save(user);
    }

    @Override
    public Page<UserSession> userSessions(UserSessionQo qo) throws Exception {
        return userSessionRepository.findAll((Pageable) qo);
    }

    @Override
    public UserSession findByUserSessionUserId(Integer id) throws Exception {
        UserSession userSession = userSessionRepository.findByUserId(id);
        if (userSession == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return userSession;
    }

    //考试记录，收藏信息（总数），错题本（总数），个人信息，注册时间，登录日志

    @Override
    public OmsProfile omsProfile(Integer id) throws Exception {
        User user = getById(id);
        OmsProfile omsProfile = new OmsProfile();
        Mistakes mistakes = mistakesService.findByUserId(id);
        Integer totalUsrPaper = usrPaperService.totalNumber(id);
        Integer totalCollect = collectService.findByUserId(id).size();
        Integer totalMistakes = mistakes.getQuestionIds().size();
        omsProfile.setUsername(user.getUsername());
        omsProfile.setName(user.getName());
        omsProfile.setMobile(user.getMobile());
        omsProfile.setEmail(user.getEmail());
        omsProfile.setAvatar(user.getAvatar());
        omsProfile.setStatus(user.getStatus());
        omsProfile.setSex(user.getSex());
        omsProfile.setSignupAt(user.getSignupAt());
        omsProfile.setTotalUsrPaper(totalUsrPaper);
        omsProfile.setTotalCollect(totalCollect);
        omsProfile.setTotalMistakes(totalMistakes);
        omsProfile.setUserSession(findByUserSessionUserId(id));
        return omsProfile;
    }
}

