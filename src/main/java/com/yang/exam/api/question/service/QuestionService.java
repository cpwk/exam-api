package com.yang.exam.api.question.service;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.qo.QuestionQo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 17:04
 * @Version：1.0
 */
public interface QuestionService {

    void save(Question question) throws Exception;

    void delete(Integer id) throws Exception;

    Question findById(Integer id);

    Question getById(Integer id);

    Page<Question> question_list(QuestionQo qo) throws Exception;

}
