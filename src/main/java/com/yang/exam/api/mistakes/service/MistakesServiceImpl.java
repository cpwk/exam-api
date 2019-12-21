package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.entity.MistakesError;
import com.yang.exam.api.mistakes.entity.MistakesOptions;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.qo.MistakesQo;
import com.yang.exam.api.mistakes.resitpory.MistakesResitpory;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
        return mistakes;
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
    public Page<Mistakes> mistakesList(MistakesQo qo, MistakesOptions options) throws Exception {
        Page<Mistakes> mistakesList = mistakesResitpory.findAll(qo);
        wrap(mistakesList.getContent(), options);
        return mistakesList;
    }

    private void wrap(Collection<Mistakes> mistakes, MistakesOptions options) throws Exception {
        if (options.isWithQuestion()) {
            for (Mistakes mistakes1 : mistakes) {
                mistakes1.setQuestion(questionService.getById(mistakes1.getQuestionId()));
            }
        }
    }

    @Override
    public Mistakes findByUserIdAndQuestionId(Integer userId, Integer questionId) throws Exception {
        return mistakesResitpory.findByUserIdAndQuestionId(userId, questionId);
    }

    @Override
    public void delete(Integer id) throws Exception {
        Mistakes exist = getById(id);
        mistakesResitpory.delete(exist);
    }
}
