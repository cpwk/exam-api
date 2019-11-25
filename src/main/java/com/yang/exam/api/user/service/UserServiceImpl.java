package com.yang.exam.api.user.service;

import com.yang.exam.api.support.SupportService.SupportService;
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

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 20:01
 * @Version：1.0
 */

@Service
public class UserServiceImpl implements UserService, UserError {

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
    public UserSessionWrapper signIn(User user, VCode vCode) throws Exception {
        User exist = null;
        if (StringUtils.isEmail(user.getUserName())) {
            exist = userRepository.findByEmail(user.getUserName());
            VCode v = supportService.getVcode(vCode.getKey());
            equally(vCode, v, user);
        } else if (StringUtils.isChinaMobile(user.getUserName())) {
            exist = userRepository.findByMobile(user.getUserName());
            VCode v = supportService.getVcode(vCode.getKey());
            equally(vCode, v, user);
        } else {
            exist = userRepository.findByUserName(user.getUserName());
            if (!StringUtils.checkPassword(user.getPassword())) {
                throw new ServiceException(ERROR_PASSWORD_INVALID);
            }
            if (!exist.getPassword().equals(StringUtils.encryptPassword(user.getPassword(), salt))) {
                throw new ServiceException(ERROR_PASSWORD_INVALID);
            }
        }

        UserSession session = createSession(exist);
        return new UserSessionWrapper(exist, session);

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

    private void equally(VCode vCode, VCode v, User user) throws Exception {
        if (StringUtils.isNotEqual(vCode.getCode(), v.getCode())
                && StringUtils.isNotEqual(vCode.getAccount(), user.getUserName())) {
            throw new ServiceException(ERROR_VCODE_INVALID);
        }
    }
}
