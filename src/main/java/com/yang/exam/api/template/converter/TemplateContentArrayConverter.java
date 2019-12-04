package com.yang.exam.api.template.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yang.exam.api.template.entity.TemplateContent;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/29 11:21
 * @Version：1.0
 */
public class TemplateContentArrayConverter implements AttributeConverter<List<TemplateContent>, String> {

    @Override
    public String convertToDatabaseColumn(List<TemplateContent> list) {
        return JSON.toJSONString(list);
    }

    @Override
    public List<TemplateContent> convertToEntityAttribute(String data) {
        try {
            return JSONArray.parseArray(data, TemplateContent.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
