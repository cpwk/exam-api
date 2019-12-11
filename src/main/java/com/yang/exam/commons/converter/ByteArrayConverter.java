package com.yang.exam.commons.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class ByteArrayConverter implements AttributeConverter<List<Byte>, String> {

    @Override
    public String convertToDatabaseColumn(List<Byte> list) {
        return JSON.toJSONString(list);
    }

    @Override
    public List<Byte> convertToEntityAttribute(String data) {
        try {
            return JSONArray.parseArray(data, Byte.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
