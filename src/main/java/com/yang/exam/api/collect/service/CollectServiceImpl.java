package com.yang.exam.api.collect.service;

import com.yang.exam.api.collect.entity.CollectError;
import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.api.collect.qo.CollectQo;
import com.yang.exam.api.collect.resitpory.CollectResitpory;
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
 * @Date: 2019/12/18 19:02
 * @Version：1.0
 */
@Service
public class CollectServiceImpl implements CollectService, CollectError {

    @Autowired
    private CollectResitpory collectResitpory;

    @Autowired
    private QuestionService questionService;

    @Override
    public void save(Collect collect) throws Exception {
        if (collect.getCreatedAt() == null) {
            collect.setCreatedAt(System.currentTimeMillis());
        }
        if (collect.getStatus() == null) {
            collect.setStatus(STATUS_OK);
        }
        if (collectResitpory.findByUserIdAndQuestionId(collect.getUserId(), collect.getQuestionId()) == null) {
            collectResitpory.save(collect);
        }
    }

    @Override
    public Collect findById(Integer id) throws Exception {
        return collectResitpory.findById(id).orElse(null);
    }

    @Override
    public Collect getById(Integer id) throws Exception {
        Collect collect = findById(id);
        if (collect == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return collect;
    }

    @Override
    public void status(Integer id) throws Exception {
        Collect exist = getById(id);
        if (exist.getStatus().equals(STATUS_OK)) {
            exist.setStatus(STATUS_HALT);
        } else {
            exist.setStatus(STATUS_OK);
        }
        save(exist);
    }

    @Override
    public List<Question> collectList(CollectQo collectQo) throws Exception {
        List<Question> questions = new ArrayList<>();
        Page<Collect> collects = collectResitpory.findAll(collectQo);
        for (Collect collect : collects) {
            questions.add(questionService.getById(collect.getQuestionId()));
        }
        return questions;
    }
}
