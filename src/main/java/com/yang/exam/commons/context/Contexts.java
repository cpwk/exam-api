package com.yang.exam.commons.context;


import com.yang.exam.api.user.authority.UserSessionWrap;
import com.yang.exam.commons.entity.ErrorCode01;
import com.yang.exam.commons.exception.DetailedException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.resources.LocaleBundles;

public class Contexts {

    public static void set(Context context) {
        SessionThreadLocal.getInstance().set(context);
    }

    public static Context get() {
        return SessionThreadLocal.getInstance().get();
    }

    public static SessionWrapper getSession() {
        return get().getSession();
    }

    public static String ensureLocale() {
        Context context = get();
        if (context == null) {
            return LocaleBundles.getDefaultLocale();
        }
        return context.getLocale();
    }


    //
    //

    public static Integer requestUserId() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ErrorCode01.SESSIONTIMEOUT.getCode());
        }
        Integer id = sessionUserId();
        if (id == null) {
            throw new DetailedException("need userId");
        }
        return id;
    }

    public static Integer sessionUserId() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrapper wrap = context.getSession();
        Integer id = null;

        if (wrap instanceof UserSessionWrap) {
            id = ((UserSessionWrap) wrap).getUser().getId();
        }
        return id;
    }

}
