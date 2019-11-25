package com.yang.exam.commons.context;


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

}
