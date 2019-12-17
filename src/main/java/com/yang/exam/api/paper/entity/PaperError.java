package com.yang.exam.api.paper.entity;

import com.yang.exam.commons.exception.ErrorCode;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/3 13:29
 * @Version：1.0
 */
public interface PaperError extends ErrorCode {
    public static final int ERR_PAPER_NAME_EMPTY = 1500;
}

