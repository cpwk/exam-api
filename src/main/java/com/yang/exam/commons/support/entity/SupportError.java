package com.yang.exam.commons.support.entity;


import com.yang.exam.commons.exception.ErrorCode;

public interface SupportError extends ErrorCode {

    public static final int ERROR_MOBILE_VCODE_OVERTIME = 1201;
    public static final int ERROR_MOBILE_INVALID = 1202;
    public static final int ERROR_ALIYUN_EXCEPTION = 1203;
    public static final int ERROR_VCODE_OVERTIME = 1204;
    public static final int ERROR_VCODE_INVALID = 1205;
    public static final int ERROR_MOBILE_OCCUPY = 1206;
    public static final int ERROR_SEND_FAIL = 1207;

}
