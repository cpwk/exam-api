package com.yang.exam.commons.common.service;


import com.yang.exam.commons.support.model.VCode;

public interface ICommonService {

    void saveVCode(Long key, VCode vCode);

    VCode getValCode(Long key) throws Exception;

}
