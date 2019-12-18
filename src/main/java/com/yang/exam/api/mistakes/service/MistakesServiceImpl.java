package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.entity.MistakesError;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.qo.MistakesQo;
import com.yang.exam.api.mistakes.resitpory.MistakesResitpory;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

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
    public Mistakes findById(Integer id) throws Exception {
        return mistakesResitpory.findById(id).orElse(null);
    }

    @Override
    public Mistakes getById(Integer id) throws Exception {
        Mistakes mistakes = findById(id);
        if (mistakes == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return null;
    }

    @Override
    public void save(Mistakes mistakes) {
        if (mistakes.getStatus() == null) {
            mistakes.setStatus(STATUS_OK);
        }
        if (mistakesResitpory.findByUserIdAndQuestionId(mistakes.getUserId(), mistakes.getQuestionId()) == null) {
            mistakesResitpory.save(mistakes);
        }
    }

    @Override
    public List<Question> mistakesList(MistakesQo qo) throws Exception {

        Page<Mistakes> mistakesList = mistakesResitpory.findAll(qo);
        List<Question> list = new ArrayList<>();
        for (Mistakes mistakes : mistakesList) {
            list.add(questionService.getById(mistakes.getQuestionId()));
        }
        return list;
    }

    @Override
    public Mistakes findByUserIdAndQuestionId(Integer userId, Integer questionId) throws Exception {
        return mistakesResitpory.findByUserIdAndQuestionId(userId, questionId);
    }

    @Override
    public void status(Integer id) throws Exception {
        Mistakes exist = getById(id);
        if (exist.getStatus().equals(STATUS_OK)) {
            exist.setStatus(STATUS_HALT);
        } else {
            exist.setStatus(STATUS_OK);
        }
        save(exist);
    }
}
