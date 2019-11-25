package com.yang.exam.api.question.model;

import com.yang.exam.commons.exception.ErrorCode;

public interface QuestionError extends ErrorCode {
    public static final int ERR_COMPLETE_EMPTY = 1100;//确保信息填写完整
}
