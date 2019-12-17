package com.yang.exam.api.template.entity;

import com.yang.exam.commons.exception.ErrorCode;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/2 16:54
 * @Version：1.0
 */
public interface TemplateError extends ErrorCode {
    public static final int ERR_DIFFICULTY_EMPTY = 1400;
    public static final int ERR_TEMPLATE_NAME_EMPTY = 1401;
    public static final int ERR_CATEGORYID_EMPTY = 1402;
    public static final int ERR_CONTENT_EMPTY = 1403;
    public static final int ERR_DURATION_EMPTY = 1404;
}
