package com.yang.exam.api.question.entity;

import com.yang.exam.commons.exception.ErrorCode;

public interface QuestionError extends ErrorCode {
    public static final int ERR_QUESTION_TOPIC_EMPTY = 1100;
    public static final int ERR_QUESTION_DIFFICULTY_EMPTY = 1101;
    public static final int ERR_QUESTION_TYPE_EMPTY = 1102;
    public static final int ERR_QUESTION_CATEGORYID_EMPTY = 1103;
    public static final int ERR_QUESTION_ANSWER_EMPTY = 1104;
    public static final int ERR_QUESTION_TAGSID_EMPTY = 1105;
    public static final int ERR_QUESTION_OPTIONS_EMPTY = 1106;
    public static final int ERR_QUESTION_ANSWER_MIN = 1107;
}
