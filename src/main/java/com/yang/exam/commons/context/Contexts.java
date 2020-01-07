package com.yang.exam.commons.context;


import com.yang.exam.api.admin.entity.AdminSessionWrap;
import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.user.authority.UserSessionWrap;
import com.yang.exam.api.user.model.User;
import com.yang.exam.commons.exception.DetailedException;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.resources.LocaleBundles;

public class Contexts implements ErrorCode {

    public static void set(Context context) {
        SessionThreadLocal.getInstance().set(context);
    }

    public static Context get() {
        return SessionThreadLocal.getInstance().get();
    }

    public static SessionWrap getSession() {
        return get().getSession();
    }

    public static String ensureLocale() {
        Context context = get();
        if (context == null) {
            return LocaleBundles.getDefaultLocale();
        }
        return context.getLocale();
    }

    public static User requestUser() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        User user = sessionUser();
        if (user == null) {
            throw new DetailedException("need userId");
        }
        return user;
    }

    public static User sessionUser() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrap wrap = context.getSession();
        User user = null;
        if (wrap instanceof UserSessionWrap) {
            user = ((UserSessionWrap) wrap).getUser();
        }
        return user;
    }

    public static Admin requestAdmin() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        Admin admin = sessionAdmin();
        if (admin == null) {
            throw new DetailedException("need userId");
        }
        return admin;
    }

    public static Admin sessionAdmin() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrap wrap = context.getSession();
        Admin admin = null;
        if (wrap instanceof AdminSessionWrap) {
            admin = ((AdminSessionWrap) wrap).getAdmin();
        }
        return admin;
    }

}
