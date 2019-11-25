package com.yang.exam.api.user.service;

import com.yang.exam.api.support.model.VCode;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.model.UserSessionWrapper;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 20:00
 * @Version：1.0
 */
public interface UserService {

    UserSessionWrapper signIn(User user, VCode vCode) throws Exception;

}
