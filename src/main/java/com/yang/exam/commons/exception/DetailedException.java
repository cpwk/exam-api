package com.yang.exam.commons.exception;


import com.yang.exam.commons.entity.ErrorCode01;

public class DetailedException extends ServiceException {

    public DetailedException(String msg) {
        super(ErrorCode01.DETAILED.getCode(), msg);
    }

}
