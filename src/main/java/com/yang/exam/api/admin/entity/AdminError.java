package com.yang.exam.api.admin.entity;

import com.yang.exam.commons.exception.ErrorCode;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 15:05
 * @Version：1.0
 */
public interface AdminError extends ErrorCode {
    public static final int ERR_ADMIN_USERNAME_INVALID = 1000;
    public static final int ERR_ADMIN_PASSWORD_INVALID = 1001;
    public static final int ERR_ADMIN_OLDPASSWORD_INVALID = 1002;

}
