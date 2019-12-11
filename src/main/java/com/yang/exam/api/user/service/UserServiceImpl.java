package com.yang.exam.api.user.service;

import com.yang.exam.api.admin.model.AdminSession;
import com.yang.exam.api.support.SupportService.SupportService;
import com.yang.exam.api.support.model.SupportError;
import com.yang.exam.api.support.model.VCode;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.model.UserError;
import com.yang.exam.api.user.model.UserSession;
import com.yang.exam.api.user.model.UserSessionWrapper;
import com.yang.exam.api.user.qo.UserQo;
import com.yang.exam.api.user.repository.UserRepository;
import com.yang.exam.api.user.repository.UserSessionRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.ipseeker.IPSeekerUtil;
import com.yang.exam.commons.model.Constants;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

@Service
public class UserServiceImpl implements UserService, UserError, SupportError {

    private static final int NAME_LENGTH_MIN = 2;
    private static final int NAME_LENGTH_MAX = 20;
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
    private UserSessionRepository userSessionRepository;

    @Override
    public UserSessionWrapper signin(User user, VCode vCode, String ip) throws Exception {

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
            if ((exist = userRepository.findByUsername(user.getUsername())) == null) {
                throw new ServiceException(ERR_USER_USERNAME_INVALID);
            }
            if (exist.getStatus() == STATUS_HALT) {
                throw new ServiceException(ERR_USER_DISABLE);
            }
            if (!StringUtils.checkPassword(user.getPassword())) {
                throw new ServiceException(ERR_USER_PASSWORD_INVALID);
            }
            if (!exist.getPassword().equals(StringUtils.encryptPassword(user.getPassword(), salt))) {
                throw new ServiceException(ERR_USER_PASSWORD_INVALID);
            }
        }
        UserSession session = createSession(exist, ip);
        return new UserSessionWrapper(exist, session);
    }

    private void compare(VCode vCode, VCode vc, String username) {
        if (StringUtils.isEmpty(vCode.getCode()) || StringUtils.isNotEqual(username, vc.getAccount()) || StringUtils.isNotEqual(vCode.getCode(), vc.getCode())) {
            throw new ServiceException(ERROR_VCODE_INVALID);
        }
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

    @Override
    public void signup(User user, VCode vCode) throws Exception {

        if (user.getUsername().length() < USERNAME_LENGTH_MIN || user.getUsername().length() > USERNAME_LENGTH_MAX) {
            throw new ServiceException(ERR_USER_USERNAME);
        }
        if (!StringUtils.isChinaMobile(user.getMobile())) {
            throw new ServiceException(ERR_MOBILE_INVALID);
        }
        VCode vc = supportService.getVcode(vCode.getKey());
        if (StringUtils.isNotEqual(user.getMobile(), vc.getAccount())) {
            throw new ServiceException(ERR_VCODE_EMPTY);
        }
        if (userRepository.findByMobile(user.getMobile()) != null) {
            throw new ServiceException(SupportError.ERROR_MOBILE_OCCUPY);
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ServiceException(ERR_USERNAME_EXISTENCE);
        }
        if (!StringUtils.checkPassword(user.getPassword())) {
            throw new ServiceException(ERR_USER_PASSWORD_LENGTH);
        }
        user.setStatus(STATUS_OK);
        user.setAvatar("http://feibendepaopao.oss-cn-beijing.aliyuncs.com/exam/img/2019/12/6/5de9ff5bf2c4dc3f784174e4bu9h53u4.jpg");
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
                throw new ServiceException(ERROR_VCODE_INVALID);
            }
            checkPassword(user, exist);
        } else if (StringUtils.isChinaMobile(user.getUsername())) {
            if (userRepository.findByMobile(user.getUsername()) == null) {
                throw new ServiceException(ERR_USER_EMAIL_INVALID);
            }
            User exist = userRepository.findByMobile(user.getUsername());
            if (StringUtils.isNotEqual(user.getMobile(), vc.getAccount())
                    && StringUtils.isNotEqual(vCode.getCode(), vc.getCode())) {
                throw new ServiceException(ERROR_VCODE_INVALID);
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
    public Map profile(User user) throws Exception {
        Integer id = user.getId();
        User exist = getById(id);
        exist.setAvatar(user.getAvatar());
        exist.setName(user.getName());
        userRepository.save(exist);
        Map<String, User> map = new HashMap<>();
        map.put("user", exist);
        return map;
    }

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
    public UserSession findSessionByToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        return userSessionRepository.findByToken(token);
    }
}


