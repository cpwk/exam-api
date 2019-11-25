package com.yang.exam.api.admin.service;

import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.model.AdminError;
import com.yang.exam.api.admin.model.AdminSession;
import com.yang.exam.api.admin.model.AdminSessionWrapper;
import com.yang.exam.api.admin.repository.AdminRepostiory;
import com.yang.exam.api.admin.repository.AdminSessionRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.model.Constants;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService, AdminError {

    @Value("${admin.salt}")
    private String salt;

    @Value("${admin.session-days}")
    private int sessionDays;

    @Autowired
    private AdminRepostiory adminRepostiory;
    @Autowired
    private AdminSessionRepository adminSessionRepository;

    @Override
    public AdminSessionWrapper signIn(Admin admin) throws Exception {
        if (StringUtils.isEmpty(admin.getUserName())) {
            throw new ServiceException(ERR_ADMIN_USERNAME_INVALID);
        }
        if (!StringUtils.checkPassword(admin.getPassword())) {
            throw new ServiceException(ERR_ADMIN_PASSWORD_INVALID);
        }
        Admin exist = adminRepostiory.findByUserName(admin.getUserName());
        if (exist == null) {
            throw new ServiceException(ERR_ADMIN_USERNAME_INVALID);
        }
        if (!exist.getPassword().equals(StringUtils.encryptPassword(admin.getPassword(), salt))) {
            throw new ServiceException(ERR_ADMIN_PASSWORD_INVALID);
        }

        AdminSession session = createSession(exist);
        return new AdminSessionWrapper(exist, session);

    }

    private AdminSession createSession(Admin admin) {
        AdminSession session = new AdminSession();
        session.setAdminId(admin.getId());
        session.setToken(StringUtils.randomString(salt, Constants.ADMIN_TOKEN_LENGTH));
        Long now = System.currentTimeMillis();
        session.setSigninAt(now);
        session.setExpireAt(now + sessionDays * Constants.DAY_MILLIS);
        adminSessionRepository.save(session);
        return session;
    }
}
