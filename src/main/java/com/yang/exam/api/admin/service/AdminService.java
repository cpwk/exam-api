package com.yang.exam.api.admin.service;


import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.entity.AdminSession;
import com.yang.exam.api.admin.entity.AdminSessionWrapper;

public interface AdminService {

    AdminSessionWrapper signIn(Admin admin) throws Exception;

    AdminSession findSessionByToken(String token) throws Exception;

    Admin getById(Integer id) throws Exception;

    Admin findById(Integer id) throws Exception;

}
