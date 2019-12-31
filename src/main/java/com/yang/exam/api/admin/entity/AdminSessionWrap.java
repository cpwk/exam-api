package com.yang.exam.api.admin.entity;


import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.commons.context.SessionWrap;

public class AdminSessionWrap implements SessionWrap {
    private Admin admin;
    private AdminSession adminSession;

    public AdminSessionWrap(Admin amdin, AdminSession adminSession) {
        this.admin = amdin;
        this.adminSession = adminSession;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public AdminSession getAdminSession() {
        return adminSession;
    }

    public void setAdminSession(AdminSession adminSession) {
        this.adminSession = adminSession;
    }
}
