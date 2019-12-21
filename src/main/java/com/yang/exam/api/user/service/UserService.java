package com.yang.exam.api.user.service;


import com.yang.exam.api.support.model.VCode;
import com.yang.exam.api.user.authority.UserSessionWrap;
import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.qo.UserQo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserService {

    UserSessionWrap signin(User user, VCode vCode, String ip) throws Exception;

    void signup(User user, VCode vCode) throws Exception;

    void resetPassword(User user, VCode vCode) throws Exception;

    User findById(Integer id);

    User getById(Integer id);

    Page<User> users(UserQo userQo) throws Exception;

    void status(Integer id);

    UserSession findSessionByToken(String token) throws Exception;

    User user(int id, boolean init);

    Map profile() throws Exception;

    Map modifyProfile(User user) throws Exception;

}
