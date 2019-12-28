package com.yang.exam.api.question.service;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.service.CategoryService;
import com.yang.exam.api.question.entity.QuestionError;
import com.yang.exam.api.question.entity.QuestionOptions;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.qo.QuestionQo;
import com.yang.exam.api.question.repository.QuestionRepository;
import com.yang.exam.api.questionTag.model.QuestionTag;
import com.yang.exam.api.questionTag.service.QuestionTagService;
import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.api.tag.service.TagService;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.CollectionUtil;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

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
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QuestionTagService questionTagService;

    @Override
    public void save(Question question) throws Exception {
        dataCheck(question);
        time(question);
        questionRepository.save(question);
        for (int value : question.getTagsId()) {
            QuestionTag questionTag = new QuestionTag();
            questionTag.setTagId(value);
            questionTag.setQuestionId(question.getId());
            questionTagService.save(questionTag);
        }
    }

    @Override
    public Question findById(Integer id) throws Exception {
        Question question = questionRepository.findById(id).orElse(null);
        if (question != null) {
            wrap(Collections.singletonList(question), QuestionOptions.getDefaultInstance());
        }
        return question;
    }

    @Override
    public Question getById(Integer id) throws Exception {
        Question question = findById(id);
        if (question == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return question;
    }

    @Override
    public List<Question> findListByIds(Collection<Integer> ids) throws Exception {
        List<Question> items = questionRepository.findAllById(ids);
        if (CollectionUtil.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items;
    }

    @Override
    public Map<Integer, Question> findByIds(Collection<Integer> ids) throws Exception {
        List<Question> items = findListByIds(ids);
        return items.stream().collect(Collectors.toMap(Question::getId, item -> item));

//         Map<Integer,Question> map = new HashMap<>();
//         for(Question q : items){
//             map.put(q.getId(),q);
//         }
//        return map;
    }

    @Override
    public void status(Integer id) throws Exception {
        Question exist = findById(id);
        if (exist == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        if (exist.getStatus().equals(STATUS_OK)) {
            exist.setStatus(STATUS_HALT);
        } else {
            exist.setStatus(STATUS_OK);
        }
        save(exist);
    }

    @Override
    public Page<Question> questionList(QuestionQo qo, QuestionOptions options) throws Exception {
        Page<Question> questions = questionRepository.findAll(qo);
        wrap(questions.getContent(), options);
        return questions;
    }

    @Override
    public List<Integer> randomQuestionList(Integer categoryId, Byte type, Byte difficulty, Byte status, Integer limit) throws Exception {
        return questionRepository.randomQuestionList(categoryId, type, difficulty, status, limit);
    }

    private void wrap(Collection<Question> questions, QuestionOptions options) throws Exception {
        if (options.isWithCategory()) {
            List<Integer> categoryIds = questions.stream().map(Question::getCategoryId).collect(Collectors.toList());
            Map<Integer, Category> categoryMap = categoryService.findByids(categoryIds);
            for (Question question : questions) {
                question.setCategory(categoryMap.get(question.getCategoryId()));
            }
        }
        if (options.isWithTag()) {
            Set<Integer> tagIds = new HashSet<>();
            for (Question q : questions) {
                tagIds.addAll(q.getTagsId());
            }
            Map<Integer, Tag> tagMap = tagService.findTagByIds(tagIds);
            for (Question q : questions) {
                List<Tag> tags = new ArrayList<>();
                for (Integer tid : q.getTagsId()) {
                    tags.add(tagMap.get(tid));
                }
                q.setTag(tags);
            }
        }
    }

    private void dataCheck(Question question) {
        if (question.getDifficulty() == null) {
            throw new ServiceException(ERR_QUESTION_DIFFICULTY_EMPTY);
        }
        if (question.getCategoryId() == null) {
            throw new ServiceException(ERR_QUESTION_CATEGORYID_EMPTY);
        }
        if (question.getTagsId().size() < 1) {
            throw new ServiceException(ERR_QUESTION_TAGSID_EMPTY);
        }
        if (question.getType() == null) {
            throw new ServiceException(ERR_QUESTION_TYPE_EMPTY);
        }
        if (StringUtils.isEmpty(question.getTopic())) {
            throw new ServiceException(ERR_QUESTION_TOPIC_EMPTY);
        }
        if ((question.getType() == 1 || question.getType() == 2) && question.getOptions().size() < 4) {
            throw new ServiceException(ERR_QUESTION_OPTIONS_EMPTY);
        }
        if (StringUtils.isEmpty(question.getAnswer())) {
            throw new ServiceException(ERR_QUESTION_ANSWER_EMPTY);
        }
    }

    private void time(Question question) {
        if (question.getId() == null) {
            question.setCreatedAt(System.currentTimeMillis());
            question.setUpdatedAt(System.currentTimeMillis());
        } else {
            question.setUpdatedAt(System.currentTimeMillis());
        }
    }
}
