package com.yang.exam.api.user.service;


import com.yang.exam.api.support.model.VCode;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.model.UserSessionWrapper;

import java.util.Map;

public interface UserService {

    UserSessionWrapper signin(User user, VCode vCode) throws Exception;

    void signup(User user, VCode vCode) throws Exception;

    void resetPassword(User user, VCode vCode) throws Exception;

    User findById(Integer id);

    User getById(Integer id);

    Map update_personal(User user) throws Exception;
}
