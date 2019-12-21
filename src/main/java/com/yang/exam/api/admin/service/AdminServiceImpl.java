package com.yang.exam.api.admin.service;

import com.yang.exam.api.admin.entity.AdminError;
import com.yang.exam.api.admin.entity.AdminSession;
import com.yang.exam.api.admin.entity.AdminSessionWrapper;
import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.repository.AdminRepostiory;
import com.yang.exam.api.admin.repository.AdminSessionRepository;
import com.yang.exam.commons.exception.ArgumentServiceException;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.model.Constants;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.yang.exam.commons.exception.ErrorCode.ERR_SESSION_EXPIRES;


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

    @Override
    public AdminSession findSessionByToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        return adminSessionRepository.findByToken(token);
    }

    @Override
    public Admin findById(Integer id) throws Exception {
        return adminRepostiory.findById(id).orElse(null);
    }

    @Override
    public Admin getById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        Admin admin = findById(id);
        if (admin == null) {
            throw new ServiceException(ErrorCode.ERR_DATA_NOT_FOUND);
        }
        return admin;
    }
}
