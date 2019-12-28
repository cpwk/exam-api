package com.yang.exam.api.user.authority;

import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.api.user.model.User;
import com.yang.exam.commons.context.SessionWrap;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 00:21
 * @Version：1.0
 */
public class UserSessionWrap implements SessionWrap {

    private User user;
    //    @JSONField(serialize = false)
    private UserSession userSession;

    public UserSessionWrap(User user, UserSession userSession) {
        this.user = user;
        this.userSession = userSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}

