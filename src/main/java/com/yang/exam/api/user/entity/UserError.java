package com.yang.exam.api.user.entity;


import com.yang.exam.commons.exception.ErrorCode;

public interface UserError extends ErrorCode {
    public static final int ERR_USER_USERNAME_INVALID = 1000;
    public static final int ERR_USER_PASSWORD_INVALID = 1001;
    public static final int ERR_USER_OLD_PASSWORD_WRONG = 1002;
    public static final int ERR_USER_USERNAME = 1003;
    public static final int ERR_MOBILE_INVALID = 1004;
    public static final int ERR_USER_PASSWORD_LENGTH = 1005;
    public static final int ERR_USERNAME_EXISTENCE = 1006;
    public static final int ERR_VCODE_EMPTY = 1007;
    public static final int ERR_USER_EMAIL_INVALID = 1008;
    public static final int ERR_USER_DISABLE = 1009;
    public static final int ERR_PASSWORD_EMPTY = 1010;


}
