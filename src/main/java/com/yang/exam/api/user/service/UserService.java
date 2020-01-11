package com.yang.exam.api.user.service;


import com.yang.exam.api.user.authority.UserSessionWrap;
import com.yang.exam.api.user.entity.OmsProfile;
import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.qo.UserQo;
import com.yang.exam.api.user.qo.UserSessionQo;
import com.yang.exam.commons.support.model.VCode;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserService {

    //oms
    Page<User> users(UserQo userQo) throws Exception;

    void status(Integer id);

    Page<UserSession> userSessions(UserSessionQo qo) throws Exception;

    UserSession findByUserSessionUserId(Integer id) throws Exception;

    OmsProfile omsProfile(Integer id) throws Exception;

    //usr
    UserSessionWrap signin(User user, VCode vCode, String ip) throws Exception;

    void signup(User user, VCode vCode) throws Exception;

    void resetPassword(User user, VCode vCode) throws Exception;

    User findById(Integer id);

    User getById(Integer id);

    UserSession findSessionByToken(String token) throws Exception;

    User user(int id, boolean init);

    Map profile() throws Exception;

    Map modifyProfile(User user) throws Exception;

    UserSession userSession(String token);


}
