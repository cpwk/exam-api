package com.yang.exam.commons.authority;

import com.yang.exam.api.admin.authority.AdminPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: news-api
 * @description:
 * @author: 21936944@qq.com(ilike)
 * @create: 2019-08-16 14:26
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    SessionType[] session();

    AdminPermission[] adminPermission() default {AdminPermission.NONE};

    ActionResultType result() default ActionResultType.Json;
}
