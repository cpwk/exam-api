package com.yang.exam.api.user.model;


import com.yang.exam.commons.exception.ErrorCode;

public interface UserError extends ErrorCode {
    public static final int ERR_USER_USERNAME_INVALID = 1000;
    public static final int ERR_USER_PASSWORD_INVALID = 1001;
    public static final int ERR_USER_OLD_PASSWORD_WRONG = 1002;
    public static final int ERR_USER_USERNAME = 1003;
    public static final int ERR_USER_NAME = 1008;
    public static final int ERR_MOBILE_INVALID = 1004;
    public static final int ERR_USER_PASSWORD_LENGTH = 1007;
    public static final int ERR_VCODE_EMPTY = 1014;
    public static final int ERR_USERNAME_EXISTENCE = 1009;
    public static final int ERR_USER_EMAIL_FORMATINVALID = 1013;
    public static final int ERR_USER_EMAIL_EMPTY = 1015;
    public static final int ERR_USER_EMAIL_INVALID = 1016;
    public static final int ERR_USERNAME_LENGTH = 1017;

}
