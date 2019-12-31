package com.yang.exam.commons.exception;


public class DetailedException extends ServiceException {

    public DetailedException(String msg) {
        super(ErrorCode.DETAILED, msg);
    }

}
