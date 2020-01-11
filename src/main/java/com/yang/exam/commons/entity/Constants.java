package com.yang.exam.commons.entity;

public class Constants {

    public static final byte INVALID_TYPE = 3;// 无效试卷
    public static final long TOTAL_TIME = 0;//默认用时
    public static final byte STATUS_0 = 0;// 全部
    public static final byte STATUS_OK = 1;// 默认
    public static final byte STATUS_HALT = 2;// 删除、停用、取消
    public static final int ACCOUNT_SYSTEM = 0;
    public static final int ACCOUNT_ADMIN = 1;
    public static final int ACCOUNT_USER = 2;
    public static final int DAY_MILLIS = 24 * 60 * 60 * 1000;
    public static final int ADMIN_TOKEN_LENGTH = 64;
    public static final int USER_TOKEN_LENGTH = 64;
    public static int PAGESIZE_MIN = 10;
    public static int PAGESIZE_MED = 20;
    public static int PAGESIZE_MAX = 50;
    public static int PAGESIZE_INF = 10000;
    public static int CACHE_REDIS_EXPIRE = 3600 * 48;
    public static int SESSION_EXPIRE_DAYS_ADMIN = 2;
    public static int SESSION_EXPIRE_DAYS_USER = 30;
    // 权限操作级别
    public static String LEVEL_PRIMARY = "blue";
    public static String LEVEL_IMPORTANT = "red";
    public static String LEVEL_WARNING = "orange";
    //文件
    public static int EXPIRE_UPLOAD = 60 * 60;
    public static int MAX_UPLOAD_SIZE = 200 * 1024 * 1024;

}
