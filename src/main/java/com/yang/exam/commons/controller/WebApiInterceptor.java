package com.yang.exam.commons.controller;


import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.admin.service.AdminService;
import com.yang.exam.api.user.service.UserService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.authority.SessionUtil;
import com.yang.exam.commons.context.Context;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.context.SessionWrap;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.RuntimeServiceException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @program: news-api
 * @description:
 * @author: 21936944@qq.com(ilike)
 * @create: 2019-08-15 10:46
 **/
@ControllerAdvice
public class WebApiInterceptor extends SessionUtil implements HandlerInterceptor, WebApiConstant {

    //须加注解
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    /*
     * 只有该方法返回true才能继续执行所拦截的API方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

//        response.setHeader("Access-Control-Allow-Origin", "*");

        if (CrossDomainHandler.handle(request, response)) {
            return false;
        }

        //线程上下文
        Contexts.set(new Context());

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean authorized = false;
        //获取api方法上的注解
        Action action = handlerMethod.getMethodAnnotation(Action.class);
        if (action == null) {
//            return true;
            throw new RuntimeServiceException(handlerMethod.getMethod().getName() + "needs annotation Action");
        }
//        for (SessionType session : action.session()) {
//            if (session == SessionType.ADMIN) {
//                authorized = checkAdminPermission(request);
//            } else if (session == SessionType.USER) {
//                authorized = checkUserPermission(request);
//            } else if (session == SessionType.NONE) {
//                authorized = true;
//            }
//        }

        for (SessionType session : action.session()) {
            if (session == SessionType.ADMIN) {
                //多个权限满足其一即可
                for (AdminPermission permission : action.adminPermission()) {
                    authorized = checkAdminPermission(session, request, permission.name()) != null;
                    if (authorized) {
                        break;
                    }
                }
            } else if (session == SessionType.USER) {
                authorized = checkUserPermission(session, request, null) != null;
                if (authorized) {
                    break;
                }
            } else {
                // no session
                authorized = true;
            }
            if (authorized) {
                break;
            }
        }

        //抛出token无效的异常
        if (!authorized) {
            throw new ServiceException(ErrorCode.ERR_SESSION_EXPIRES);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    private SessionWrap checkAdminPermission(Enum type, HttpServletRequest request, String permission) throws Exception {
        String token = WebUtils.getHeader(request, KEY_ADMIN_TOKEN);
        SessionWrap wrap = adminPermissionCheck(type, token, permission);
        Contexts.get().setSession(wrap);
        return wrap;
    }

    private SessionWrap checkUserPermission(Enum type, HttpServletRequest request, String permission) throws Exception {
        String token = WebUtils.getHeader(request, KEY_USER_TOKEN);
        SessionWrap wrap = adminPermissionCheck(type, token, permission);
        Contexts.get().setSession(wrap);
        return wrap;
    }

    //
//    private boolean checkAdminPermission(HttpServletRequest request) throws Exception {
//        AdminSession session = adminService.findSessionByToken(WebUtils.getHeader(request, KEY_ADMIN_TOKEN));
//        if (session == null || session.getExpireAt() < System.currentTimeMillis()) {
//            return false;
//        }
//        Admin admin = adminService.getById(session.getAdminId());
//        Contexts.get().setSession(new AdminSessionWrap(admin, session));
//        return true;
//    }
//
//    private boolean checkUserPermission(HttpServletRequest request) throws Exception {
//        UserSession session = userService.findSessionByToken(WebUtils.getHeader(request, KEY_USER_TOKEN));
//        if (session == null || session.getExpireAt() < System.currentTimeMillis()) {
//            return false;
//        }
//        User user = userService.getById(session.getUserId());
//        Contexts.get().setSession(new UserSessionWrap(user, session));
//        return true;
//    }

}



