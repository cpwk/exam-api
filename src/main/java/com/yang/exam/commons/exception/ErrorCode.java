package com.yang.exam.commons.exception;

public interface ErrorCode {
    public static final int ERR_UNKNOWN_ERROR = 1;
    public static final int ERR_ILLEGAL_ARGUMENT = 2;
    public static final int ERR_PERMISSION_DENIED = 3;
    public static final int ERR_DETAILED_MESSAGE = 4;
    public static final int ERR_SESSION_EXPIRES = 5;
    public static final int ERR_OPERATION_TOO_FREQUENT = 6;
    public static final int ERR_DATA_NOT_FOUND = 7;
    public static final int ERR_NEED_UPGRADE = 8;
    public static final int PLEASE_UPLOAD_IMAGE = 9;
    public static final int CRAWL_FAILED = 10;//抓取失败
    public static final int ONLY_SUPPORTS_WX_ARTICLES = 11;//仅支持微信文章
    public static final int EXCEEDED_MAXIMUM_FILE_UPLOAD_SIZE = 12;//超过最大文件上传大小

    public static final int DETAILED = 600;
    public static final int UNKNOWN = 601;
    public static final int UNREACHED = 602;
    public static final int SESSIONTIMEOUT = 603;
    public static final int NO_PERMISSION = 604;
    public static final int ERROR_MOBILE = 610;
    public static final int NOUSER = 620;
    public static final int ERROR_PWD = 621;
    public static final int LOGIN_STATUS = 622;
    public static final int ERROR_VALCODE = 623;
    public static final int ERROR_WX_AUTH_FAIL = 630;
    public static final int ERROR_PRODUCT_TYPE = 640;
    public static final int ERROR_PAY_TYPE = 641;
    public static final int ERROR_DOWNLOAD_TIME = 650;

}
