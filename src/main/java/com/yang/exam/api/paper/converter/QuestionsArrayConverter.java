package com.yang.exam.api.paper.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yang.exam.api.question.model.Question;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/5 19:29
 * @Version：1.0
 */
public class QuestionsArrayConverter implements AttributeConverter<List<Question>, String> {
    @Override
    public String convertToDatabaseColumn(List<Question> questions) {
        return JSON.toJSONString(questions);
    }

    @Override
    public List<Question> convertToEntityAttribute(String data) {
        try {
            return JSONArray.parseArray(data, Question.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
