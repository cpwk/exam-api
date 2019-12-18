package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.qo.MistakesQo;
import com.yang.exam.api.question.model.Question;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:12
 * @Version：1.0
 */
public interface MistakesService {

    void save(Mistakes mistakes);

    List<Question> mistakesList(MistakesQo qo) throws Exception;

    Mistakes findById(Integer id) throws Exception;

    Mistakes getById(Integer id) throws Exception;

    void status(Integer id) throws Exception;

    Mistakes findByUserIdAndQuestionId(Integer userId, Integer questionId) throws Exception;
}
