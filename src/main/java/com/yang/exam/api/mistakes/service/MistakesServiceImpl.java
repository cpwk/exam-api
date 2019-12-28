package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.entity.MistakesError;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.resitpory.MistakesResitpory;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:12
 * @Version：1.0
 */
@Service
public class MistakesServiceImpl implements MistakesService, MistakesError {

    @Autowired
    private MistakesResitpory mistakesResitpory;

    @Autowired
    private QuestionService questionService;

    @Override
    public void save(Mistakes mistakes) {
        mistakesResitpory.save(mistakes);
    }

    @Override
    public Mistakes findById(Integer id) throws Exception {
        return mistakesResitpory.findById(id).orElse(null);
    }

    @Override
    public Mistakes getById(Integer id) throws Exception {
        Mistakes mistakes = findById(id);
        if (mistakes == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return mistakes;
    }

    @Override
    public Mistakes findByUserId(Integer id) throws Exception {
        return mistakesResitpory.findByUserId(id);
    }

    @Override
    public void delete(Integer id) throws Exception {
        Mistakes exist = getById(id);
        mistakesResitpory.delete(exist);
    }

    @Override
    public Mistakes mistakesList() throws Exception {
        Integer userId = Contexts.requestUser().getId();
        Mistakes mistakes = findByUserId(userId);
        mistakes.setQuestions(questionService.findListByIds(mistakes.getQuestionId()));
        return mistakes;
    }

}
