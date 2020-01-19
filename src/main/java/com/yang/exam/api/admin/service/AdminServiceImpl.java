package com.yang.exam.api.admin.service;

import com.yang.exam.api.Role.model.Role;
import com.yang.exam.api.Role.service.RoleService;
import com.yang.exam.api.admin.entity.AdminError;
import com.yang.exam.api.admin.entity.AdminSession;
import com.yang.exam.api.admin.entity.AdminSessionWrap;
import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.qo.AdminSessionQo;
import com.yang.exam.api.admin.repository.AdminRepository;
import com.yang.exam.api.admin.repository.AdminSessionRepository;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.entity.Constants;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.CollectionUtil;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService, AdminError {

    @Value("${admin.salt}")
    private String salt;

    @Value("${admin.session-days}")
    private int sessionDays;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminSessionRepository adminSessionRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public AdminSessionWrap signIn(Admin admin) throws Exception {
        if (StringUtils.isEmpty(admin.getUserName())) {
            throw new ServiceException(ERR_ADMIN_USERNAME_INVALID);
        }
        if (!StringUtils.checkPassword(admin.getPassword())) {
            throw new ServiceException(ERR_ADMIN_PASSWORD_INVALID);
        }
        Admin exist = adminRepository.findByUserName(admin.getUserName());
        if (exist == null) {
            throw new ServiceException(ERR_ADMIN_USERNAME_INVALID);
        }
        if (exist.getPassword().equals(StringUtils.encryptPassword(admin.getPassword(), salt))) {
            throw new ServiceException(ERR_ADMIN_PASSWORD_INVALID);
        }
        exist.setRole(roleService.getById(exist.getRoleId()));
        AdminSession session = createSession(exist);
        return new AdminSessionWrap(exist, session);

    }

    @Override
    public AdminSession findSessionByToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        return adminSessionRepository.findByToken(token);
    }

    @Override
    public Admin getById(Integer id) throws Exception {
        Admin admin = findById(id);
        if (admin == null) {
            throw new ServiceException(ErrorCode.ERR_DATA_NOT_FOUND);
        }
        return admin;
    }

    @Override
    public Admin findById(Integer id) throws Exception {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin admin(Integer id, boolean init) throws Exception {
        Admin admin = getById(id);
        if (init && admin != null)
            admin.setRole(roleService.getById(admin.getRoleId()));
        return admin;
    }

    @Override
    public List<Admin> admins() {
        List<Admin> admins = adminRepository.findAll(new Sort(Sort.Direction.ASC, "roleId"));
        List<Role> roles = roleService.roles(false);
        for (Admin admin : admins) {
            for (Role role : roles) {
                if (admin.getRoleId().intValue() == role.getId().intValue())
                    admin.setRole(role);
            }
        }
        return admins;
    }

    @Override
    public Page<AdminSession> adminSessions(AdminSessionQo qo) throws Exception {
        return adminSessionRepository.findAll(qo);
    }

    @Override
    public void save_admin(Admin admin) throws ServiceException {
        Admin oa = getAdmin(admin.getUserName());
        if (admin.getId() != null && admin.getId() > 0) {
            if (oa == null)
                throw new ServiceException(NOUSER);
            oa.setName(admin.getName());
            oa.setRoleId(admin.getRoleId());
            if (!StringUtils.isNull(admin.getPassword()))
                oa.setPassword(StringUtils.encryptPassword(admin.getPassword(), salt));
            adminRepository.save(oa);
        } else {
            if (oa != null) {
                throw new ServiceException(ERR_ADMIN_USERNAME_INVALID);
            }
            admin.setPassword(StringUtils.encryptPassword(admin.getPassword(), salt));
            admin.setStatus(Constants.STATUS_OK);
            adminRepository.save(admin);
        }
    }

    @Override
    public void remove_admin(int id) throws ServiceException {
        if (id == 1) {
            throw new ServiceException(UNREACHED);
        }
        adminRepository.deleteById(id);
    }

    @Override
    public Map profile() throws Exception {
        Integer adminId = Contexts.requestAdmin().getId();
        Admin admin = admin(adminId, true);
        return CollectionUtil.arrayAsMap("admin", admin);
    }

    @Override
    public void update_password(String password, String oldPassword) throws Exception {
        Integer adminId = Contexts.requestAdmin().getId();
        Admin admin = getById(adminId);
        if (oldPassword.equals(StringUtils.encryptPassword(admin.getPassword(), salt))) {
            throw new ServiceException(ERR_ADMIN_OLDPASSWORD_INVALID);
        }
        admin.setPassword(StringUtils.encryptPassword(password, salt));
        adminRepository.save(admin);
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

    private Admin getAdmin(String username) {
        try {
            username = StringUtils.escapeSql(username);
            Admin admin = adminRepository.findByUserName(username);
            if (admin != null)
                admin.setRole(roleService.getById(admin.getRoleId()));
            return admin;
        } catch (Exception e) {
            return null;
        }
    }

}

