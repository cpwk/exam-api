package com.yang.exam.commons.common.service;


import com.yang.exam.commons.entity.ValCode;
import com.yang.exam.commons.exception.ServiceException;

public interface ICommonService {

    void saveValCode(Long key, ValCode valCode);

    ValCode getValCode(Long key) throws ServiceException;

}
