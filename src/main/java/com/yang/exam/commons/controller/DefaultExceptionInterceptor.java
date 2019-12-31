package com.yang.exam.commons.controller;


import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.ActionResultType;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.exception.RuntimeServiceException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.resources.LocaleBundles;
import com.yang.exam.commons.utils.L;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionInterceptor {

    private static final RuntimeServiceException DEFAULT_EXCEPTION = new RuntimeServiceException();

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleError(HttpServletRequest request, HandlerMethod handlerMethod, Throwable ex) throws Throwable {
//        L.error(ex);
        if (!(ex instanceof ServiceException)) {
            ApiLog.log(request, null);
        }
        Action action = handlerMethod.getMethodAnnotation(Action.class);
        ActionResultType resultType = action == null ? ActionResultType.Json : action.result();
        if (resultType == ActionResultType.Json) {
            ServiceException se;
            if (ex instanceof ServiceException) {
                se = (ServiceException) ex;
            } else {
                L.error(ex);
                se = DEFAULT_EXCEPTION;
            }
            int errorCode = se.getErrorCode();
            String errorMsg = LocaleBundles.getWithArrayParams(Contexts.ensureLocale(), "err." + errorCode,
                    se.getErrorParams());
            Map<String, Object> result = new HashMap<>();
            result.put("errcode", errorCode);
            result.put("errmsg", errorMsg);
            result.put("errdata", se.getErrorData());
            return new ModelAndView(new JsonView(result));
        } else {
            return null;
        }
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Throwable ex) {
        return new ModelAndView(new NotFoundView());
    }
}