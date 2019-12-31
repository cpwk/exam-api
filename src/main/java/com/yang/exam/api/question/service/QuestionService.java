package com.yang.exam.api.question.service;

import com.yang.exam.api.question.entity.QuestionOptions;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.qo.QuestionQo;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 17:04
 * @Version：1.0
 */
public interface QuestionService {
    void save(Question question) throws Exception;

    Question findById(Integer id) throws Exception;

    Question getById(Integer id) throws Exception;

    List<Question> findListByIds(Collection<Integer> ids) throws Exception;

    Map<Integer, Question> findByIds(Collection<Integer> ids) throws Exception;

    void status(Integer id) throws Exception;

    Page<Question> questionList(QuestionQo qo, QuestionOptions options) throws Exception;

    List<Integer> randomQuestionList(Integer categoryId, Byte difficulty, Byte status, Byte type, Integer limit) throws Exception;
}
