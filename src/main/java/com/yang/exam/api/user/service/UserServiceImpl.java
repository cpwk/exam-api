package com.yang.exam.api.user.service;

import com.yang.exam.api.support.SupportService.SupportService;
import com.yang.exam.api.support.model.SupportError;
import com.yang.exam.api.support.model.VCode;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.model.UserError;
import com.yang.exam.api.user.model.UserSession;
import com.yang.exam.api.user.model.UserSessionWrapper;
import com.yang.exam.api.user.repository.UserRepository;
import com.yang.exam.api.user.repository.UserSessionRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.model.Constants;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    public UserSessionWrapper signin(User user, VCode vCode) throws Exception {

        User exist = null;
        if (StringUtils.isEmail(user.getUsername())) {
            exist = userRepository.findByEmail(user.getUsername());
            VCode vc = supportService.getVcode(vCode.getKey());
            compare(vCode, vc, user.getUsername());
        } else if (StringUtils.isChinaMobile(user.getUsername())) {
            exist = userRepository.findByMobile(user.getUsername());
            VCode vc = supportService.getVcode(vCode.getKey());
            compare(vCode, vc, user.getUsername());
        } else {
            exist = userRepository.findByUsername(user.getUsername());
            if (!StringUtils.checkPassword(user.getPassword())) {
                throw new ServiceException(ERR_USER_PASSWORD_INVALID);
            }
            if (!exist.getPassword().equals(StringUtils.encryptPassword(user.getPassword(), salt))) {
                throw new ServiceException(ERR_USER_PASSWORD_INVALID);
            }
        }
        UserSession session = createSession(exist);
        return new UserSessionWrapper(exist, session);
    }

    private void compare(VCode vCode, VCode vc, String username) {
        if (StringUtils.isEmpty(vCode.getCode()) || StringUtils.isNotEqual(username, vc.getAccount()) || StringUtils.isNotEqual(vCode.getCode(), vc.getCode())) {
            throw new ServiceException(ERROR_VCODE_INVALID);
        }
    }

    private UserSession createSession(User user) {
        UserSession userSession = new UserSession();
        userSession.setUserId(user.getId());
        userSession.setToken(StringUtils.randomString(salt, Constants.ADMIN_TOKEN_LENGTH));
        Long now = System.currentTimeMillis();
        userSession.setSigninAt(now);
        userSession.setExpireAt(now + sessionDays * Constants.DAY_MILLIS);
        userSessionRepository.save(userSession);
        return userSession;
    }

    @Override
    public void signup(User user, VCode vCode) throws Exception {

        if (user.getUsername().length() < USERNAME_LENGTH_MIN || user.getUsername().length() > USERNAME_LENGTH_MAX) {
            throw new ServiceException(ERR_USER_USERNAME);
        }
//        if (user.getName().length() < NAME_LENGTH_MIN || user.getName().length() > NAME_LENGTH_MAX) {
//            throw new ServiceException(ERR_USER_NAME);
//        }
        if (!StringUtils.isChinaMobile(user.getMobile())) {
            throw new ServiceException(ERR_MOBILE_INVALID);
        }
//        if (!StringUtils.isEmail(user.getEmail())) {
//            throw new ServiceException(ERR_USER_EMAIL_FORMATINVALID);
//        }
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
        checkPassword(user, user);
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
    public Map update_personal(User user) throws Exception {
        Integer id = user.getId();
        User exist = getById(id);
        exist.setAvatar(user.getAvatar());
        exist.setName(user.getName());
        userRepository.save(exist);
        Map<String, User> map = new HashMap<>();
        map.put("user", exist);
        return map;
    }
}


