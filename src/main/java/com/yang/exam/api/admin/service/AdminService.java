package com.yang.exam.api.admin.service;


import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.model.AdminSessionWrapper;

public interface AdminService {
    AdminSessionWrapper signIn(Admin admin) throws Exception;

}
