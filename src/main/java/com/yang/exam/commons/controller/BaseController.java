package com.yang.exam.commons.controller;


import com.sunnysuperman.commons.bean.Bean;
import com.yang.exam.commons.exception.ArgumentServiceException;
import com.yang.exam.commons.utils.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: news-api
 * @description:
 * @author: 21936944@qq.com(ilike)
 * @create: 2019-08-15 10:37
 **/
public class BaseController {

    protected static <T> T parseModel(String modelJSON, T model)
            throws Exception {
        try {
            return Bean.fromJson(modelJSON, model, null);
        } catch (Exception e) {
            throw new ArgumentServiceException("model");
        }
    }

    protected ModelAndView feedback() {
        return feedback(null);
    }

    protected ModelAndView feedback(Object obj) {
        Object result = obj != null ? obj : "success";
        Map<String, Object> data = new HashMap<>();
        data.put("errcode", 0);
        data.put("result", result);
        return new ModelAndView(new JsonView(data));
    }

    HttpServletRequest getRequest() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ra.getRequest();
    }

    protected String getRemoteAddress() {
        return WebUtils.getRemoteAddress(getRequest());
    }
}
