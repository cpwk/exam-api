package com.yang.exam.commons.authority;

import com.yang.exam.api.admin.entity.AdminSession;
import com.yang.exam.api.admin.entity.AdminSessionWrap;
import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.service.AdminService;
import com.yang.exam.api.user.authority.UserSessionWrap;
import com.yang.exam.api.user.entity.UserSession;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.service.UserService;
import com.yang.exam.commons.context.SessionWrap;
import com.yang.exam.commons.entity.Constants;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class SessionUtil implements ErrorCode {

    public static Map<String, SessionWrap> map = new HashMap<>();
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    public static SessionWrap getSession(String token) {
        return map.get(token);
    }

    public static boolean tokenTimeout(String token) {
        if (map.get(token) == null) {
            return true;
        } else {
            SessionWrap wrap = map.get(token);
            if (wrap == null) {
                return true;
            }
            long currT = System.currentTimeMillis();
            if (wrap instanceof AdminSessionWrap) {
                AdminSession session = ((AdminSessionWrap) wrap).getAdminSession();
                return session == null || session.getExpireAt() <= currT;
            } else if (wrap instanceof UserSessionWrap) {
                UserSession session = ((UserSessionWrap) wrap).getUserSession();
                return session == null || session.getExpireAt() <= currT;
            } else {
                return true;
            }
        }

    }

    public static void putSession(String token, SessionWrap sess) {
        if (map == null || map.isEmpty()) {
            map = new HashMap<>();
        }
        map.put(token, sess);
    }

    public static void removeSession(String token) {
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (token.equals(key)) {
                iterator.remove();
                map.remove(key);
            }
        }
    }

    public SessionWrap adminPermissionCheck(Enum type, String token, String permission) throws Exception {

        if (tokenTimeout(token)) {
            long currT = System.currentTimeMillis();
            if (type == SessionType.ADMIN) {
                AdminSession session = adminService.findSessionByToken(token);

                if (session != null && session.getExpireAt() > currT) {
                    Admin admin = adminService.admin(session.getAdminId(), true);
                    if (admin != null && admin.getStatus() == Constants.STATUS_OK) {
                        AdminSessionWrap wrap = new AdminSessionWrap(admin, session);
                        putSession(token, wrap);
                        return wrap;
                    } else {
                        throw new ServiceException(NO_PERMISSION);
                    }
                } else {
                    throw new ServiceException(ERR_SESSION_EXPIRES);
                }
            } else if (type == SessionType.USER) {
                UserSession session = userService.userSession(token);

                if (session != null && session.getExpireAt() > currT) {
                    User user = userService.user(session.getUserId(), true);
                    if (user != null && user.getStatus() == Constants.STATUS_OK) {
                        UserSessionWrap wrap = new UserSessionWrap(user, session);
                        putSession(token, wrap);
                        return wrap;
                    } else {
                        throw new ServiceException(NO_PERMISSION);
                    }
                } else {
                    throw new ServiceException(ERR_SESSION_EXPIRES);
                }
            }
        }

        {
            boolean pass = false;

            SessionWrap wrap = getSession(token);
            if (wrap == null) {
                throw new ServiceException(ERR_SESSION_EXPIRES);
            }

            if (StringUtils.isEmpty(permission) || permission.equals("NONE")) {
                pass = true;
            } else {
                List<String> ps = new ArrayList<>();
                if (wrap instanceof AdminSessionWrap) {
                    Admin admin = ((AdminSessionWrap) wrap).getAdmin();
                    ps = admin.getRole().getPermissions();
                } else if (wrap instanceof UserSessionWrap) {
                    //不需要权限
                }
                pass = ps.contains(permission);
            }

            if (pass) {
                return wrap;
            } else {
                throw new ServiceException(NO_PERMISSION);
            }
        }

    }

}
