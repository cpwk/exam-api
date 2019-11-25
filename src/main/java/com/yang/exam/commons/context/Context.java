package com.yang.exam.commons.context;


public class Context {
    private String locale;
    private SessionWrapper session;

    public SessionWrapper getSession() {
        return session;
    }

    public void setSession(SessionWrapper session) {
        this.session = session;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
