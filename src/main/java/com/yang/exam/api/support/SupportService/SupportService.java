package com.yang.exam.api.support.SupportService;

import com.yang.exam.api.support.model.VCode;
import org.springframework.stereotype.Service;


@Service
public interface SupportService {

    void sendSms(VCode vCode) throws Exception;

    VCode getVcode(Long Key) throws Exception;

    void saveVCode(Long key, VCode vCode) throws Exception;


}
