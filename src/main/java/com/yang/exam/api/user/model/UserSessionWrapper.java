package com.yang.exam.api.user.model;


import com.yang.exam.commons.context.SessionWrapper;

public class UserSessionWrapper implements SessionWrapper {
    private User user;
    private UserSession userSession;

    public UserSessionWrapper(User user, UserSession userSession) {
        this.user = user;
        this.userSession = userSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}
