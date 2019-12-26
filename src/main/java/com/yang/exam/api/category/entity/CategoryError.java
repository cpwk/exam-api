package com.yang.exam.api.category.entity;


import com.yang.exam.commons.exception.ErrorCode;

public interface CategoryError extends ErrorCode {

    public static final int ERR_UNKNOWN = 1300;
    public static final int ERR_CATEGORY_NAME_EMPTY = 1301;

}
