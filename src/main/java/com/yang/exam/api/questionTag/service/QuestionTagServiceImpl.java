package com.yang.exam.api.questionTag.service;

import com.yang.exam.api.questionTag.model.QuestionTag;
import com.yang.exam.api.questionTag.repository.QuestionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 17:13
 * @Version：1.0
 */
@Service
public class QuestionTagServiceImpl implements QuestionTagService {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Override
    public void save(QuestionTag questionTag) throws Exception {
        questionTagRepository.save(questionTag);
    }
}
