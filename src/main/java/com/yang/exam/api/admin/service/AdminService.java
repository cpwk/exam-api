package com.yang.exam.api.admin.service;


import com.yang.exam.api.admin.entity.AdminSession;
import com.yang.exam.api.admin.entity.AdminSessionWrap;
import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.qo.AdminSessionQo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface AdminService {

    AdminSessionWrap signIn(Admin admin) throws Exception;

    AdminSession findSessionByToken(String token) throws Exception;

    Admin getById(Integer id) throws Exception;

    Admin findById(Integer id) throws Exception;

    Admin admin(Integer id, boolean init) throws Exception;

    List<Admin> admins();

    Page<AdminSession> adminSessions(AdminSessionQo qo) throws Exception;

    void save_admin(Admin admin) throws Exception;

    void remove_admin(int id) throws Exception;

    Map profile() throws Exception;

    void update_password(String password, String oldPassword) throws Exception;
}
