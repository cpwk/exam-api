package com.yang.exam.commons.controller;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.sunnysuperman.commons.util.JSONUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JsonSerializeManager {
    private static Map<Type, ObjectSerializer> serializers = new HashMap<>();


    public static String serialize(Object result) {
        return JSONUtil.toJSONString(result, serializers);
    }
}
