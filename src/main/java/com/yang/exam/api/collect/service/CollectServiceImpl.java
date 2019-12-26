package com.yang.exam.api.collect.service;

import com.yang.exam.api.collect.entity.CollectError;
import com.yang.exam.api.collect.entity.CollectOptions;
import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.api.collect.qo.CollectQo;
import com.yang.exam.api.collect.resitpory.CollectResitpory;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
    public void collect(Collect collect) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        Collect exist = collectResitpory.findByUserIdAndQuestionId(userId, collect.getQuestionId());
        if (exist == null) {
            Question question = questionService.getById(collect.getQuestionId());
            collect.setUserId(userId);
            collect.setType(question.getType());
            collect.setCreatedAt(System.currentTimeMillis());
            collectResitpory.save(collect);
        } else {
            collectResitpory.delete(exist);
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
    public List<Collect> findByUserId(Integer id) throws Exception {
        return collectResitpory.findByUserId(id);
    }

    @Override
    public Page<Collect> collectList(CollectQo collectQo, CollectOptions options) throws Exception {
        Page<Collect> collects = collectResitpory.findAll(collectQo);
        wrap(collects.getContent(), options);
        return collects;
    }

    private void wrap(Collection<Collect> collects, CollectOptions options) throws Exception {
        if (options.isWithQuestion()) {
            for (Collect collect : collects) {
                collect.setQuestion(questionService.getById(collect.getQuestionId()));
            }
        }
    }

}
