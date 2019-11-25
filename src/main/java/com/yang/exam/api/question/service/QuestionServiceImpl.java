package com.yang.exam.api.question.service;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.model.QuestionError;
import com.yang.exam.api.question.qo.QuestionQo;
import com.yang.exam.api.question.repository.QuestionRepository;
import com.yang.exam.api.questionTag.model.QuestionTag;
import com.yang.exam.api.questionTag.repository.QuestionTagRepository;
import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.api.tag.repository.TagRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 17:05
 * @Version：1.0
 */

@Service
public class QuestionServiceImpl implements QuestionService, QuestionError {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionTagRepository questionTagRepository;
    @Autowired
    private TagRepository tagRepository;


    @Override
    public void save(Question question) throws Exception {
        dataCheck(question);
        check(question);
        questionRepository.save(question);
        for (int value : question.getTagsId()) {
            QuestionTag questionTag = new QuestionTag();
            questionTag.setTagId(value);
            questionTag.setQuestionId(question.getId());
            questionTagRepository.save(questionTag);
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        questionRepository.deleteById(id);
    }

    @Override
    public Question findById(Integer id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public Question getById(Integer id) {
        Question question = findById(id);
        if (question == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return question;
    }

    @Override
    public Page<Question> question_list(QuestionQo qo) throws Exception {

        Page<Question> questions = questionRepository.findAll(qo);
        Set<Integer> tagIds = new HashSet<>();
        for (Question q : questions) {
            tagIds.addAll(q.getTagsId());
        }
        List<Tag> tags = tagRepository.findAllById(tagIds);
        Map<Integer, Tag> tagMap = new HashMap<>();
        for (Tag tag : tags) {
            tagMap.put(tag.getId(), tag);
        }
        List<Tag> tags1;
        for (Question qu : questions) {
            tags1 = new ArrayList<>(qu.getTagsId().size());
            for (Integer id : qu.getTagsId()) {
                tags1.add(tagMap.get(id));
                qu.setTag(tags1);
            }
        }
        return questions;
    }


    private void dataCheck(Question question) {
        if (StringUtils.isEmpty(question.getTopic())
                && question.getCategoryId() == null
                && question.getType() == null
                && StringUtils.isEmpty(question.getAnswer())
                && StringUtils.isEmpty(question.getDifficulty())) {
            throw new ServiceException(ERR_COMPLETE_EMPTY);
        }
    }

    private void check(Question question) {
        if (question.getId() == null) {
            question.setCreatedAt(System.currentTimeMillis());
            question.setUpdatedAt(System.currentTimeMillis());
        } else {
            question.setUpdatedAt(System.currentTimeMillis());
        }
    }


}
