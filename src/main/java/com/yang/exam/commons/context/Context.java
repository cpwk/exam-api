package com.yang.exam.commons.context;


public class Context {

    private String locale;

    private SessionWrap session;

    public SessionWrap getSession() {
        return session;
    }

    public void setSession(SessionWrap session) {
        this.session = session;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
