package com.yang.exam.api.admin.model;


import com.yang.exam.commons.context.SessionWrapper;

public class AdminSessionWrapper implements SessionWrapper {
    private Admin admin;
    private AdminSession adminSession;

    public AdminSessionWrapper(Admin amdin, AdminSession adminSession) {
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
