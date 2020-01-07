package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.entity.MistakesError;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.resitpory.MistakesResitpory;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.context.Contexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Mistakes findByUserId(Integer id) throws Exception {
        return mistakesResitpory.findByUserId(id);
    }

    @Override
    public void delete(Integer id) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        Mistakes mistakes = findByUserId(userId);
        List<Integer> questionsIds = mistakes.getQuestionIds();
        questionsIds.remove(id);
        mistakesResitpory.save(mistakes);
    }

    @Override
    public Mistakes mistakesList() throws Exception {
        Integer userId = Contexts.requestUser().getId();
        Mistakes mistakes = findByUserId(userId);
        if (mistakes == null) {
            return null;
        } else {
            mistakes.setQuestions(questionService.findListByIds(mistakes.getQuestionIds()));
            return mistakes;
        }
    }

}
