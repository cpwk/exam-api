package com.yang.exam.api.template.service;

import com.yang.exam.api.category.service.CategoryService;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.api.template.entity.TemplateContent;
import com.yang.exam.api.template.entity.TemplateError;
import com.yang.exam.api.template.entity.TemplateOptions;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.qo.TemplateQo;
import com.yang.exam.api.template.repository.TemplateRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/29 11:12
 * @Version：1.0
 */

@Service
public class TemplateServiceImpl implements TemplateService, TemplateError {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void save(Template template) throws Exception {
        check(template);
        if (template.getId() == null) {
            template.setCreatedAt(System.currentTimeMillis());
            template.setUpdatedAt(System.currentTimeMillis());
        } else {
            template.setUpdatedAt(System.currentTimeMillis());
        }
        templateRepository.save(template);
    }

    @Override
    public Template findById(Integer id) throws Exception {
        return templateRepository.findById(id).orElse(null);
    }

    @Override
    public Template getById(Integer id) throws Exception {
        Template template = findById(id);
        if (template == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return template;
    }

    @Override
    public void status(Integer id) throws Exception {
        Template template = findById(id);
        if (template == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        if (template.getStatus().equals(STATUS_OK)) {
            template.setStatus(STATUS_HALT);
        } else {
            template.setStatus(STATUS_OK);
        }
        save(template);
    }

    @Override
    public Page<Template> templateList(TemplateQo templateQo) throws Exception {
        Page<Template> templates = templateRepository.findAll(templateQo);
        for (Template val : templates) {
            val.setCategory(categoryService.getById(val.getCategoryId()));
        }
        return templates;
    }

    @Override
    public List<Template> template() throws Exception {
        return templateRepository.findAll();
    }

    @Override
    public Template templateId(Integer id) throws Exception {
        Template template = getById(id);
        if (template != null) {
            wrap(template, TemplateOptions.getDefaultInstance());
        }
        return template;
    }

    private void wrap(Template template, TemplateOptions options) throws Exception {
        long k = System.currentTimeMillis();
        if (options.isWithQuestions()) {
            List<Integer> questionsIds = new ArrayList<>();
            for (TemplateContent v : template.getContent()) {
                questionsIds.addAll(questionService.randomQuestionList(template.getCategoryId(), v.getType(), template.getDifficulty(), STATUS_OK, v.getNumber()));
            }
            template.setQuestions(questionService.findListByIds(questionsIds));
        }
        long i = System.currentTimeMillis();
        System.out.println("---1---" + (i - k));
    }

    private void check(Template template) throws Exception {
        if (template.getDifficulty() == null) {
            throw new ServiceException(ERR_DIFFICULTY_EMPTY);
        }
        if (StringUtils.isEmpty(template.getTemplateName())) {
            throw new ServiceException(ERR_TEMPLATE_NAME_EMPTY);
        }
        if (template.getCategoryId() == null) {
            throw new ServiceException(ERR_CATEGORYID_EMPTY);
        }
        if (template.getContent().size() < 1) {
            throw new ServiceException(ERR_CONTENT_EMPTY);
        }
        if (template.getDuration() == null) {
            throw new ServiceException(ERR_DURATION_EMPTY);
        }
    }
}


//    @Override
//    public List<Question> questions(Integer templateId) throws Exception {
//        Template template = getById(templateId);
//        List<Question> questionList = questionService.getAllByCategoryId(template.getCategoryId());
//        Random random = new Random();
//        List<Question> questions = new ArrayList<>();
//        for (TemplateContent v : template.getContent()) {
//            List<Question> questionList1 = new ArrayList<>();
//            for (Question question : questionList) {
//                if (v.getType().equals(question.getType())) {
//                    questionList1.add(question);
//                }
//            }
//            Set<Integer> set = new HashSet(v.getNumber());
//            while (set.size() < v.getNumber()) {
//                set.add(random.nextInt(questionList1.size()));
//            }
//            for (Integer integer : set) {
//                questions.add(questionList1.get(integer));
//            }
//        }
//        return questions;
//    }
//

//    private void wrapr(Template template, TemplateOptions options) throws Exception {
//        if (options.isWithQuestions()) {
//            List<Question> questionList = questionService.getAllByCategoryId(template.getCategoryId());
//            Random random = new Random();
//            List<Question> questions = new ArrayList<>();
//            for (TemplateContent v : template.getContent()) {
//                List<Question> questionList1 = questionList.stream().filter((Question question) -> question.getType().equals(v.getType())).collect(Collectors.toList());
//                Set<Integer> set = new HashSet(v.getNumber());
//                while (set.size() < v.getNumber()) {
//                    set.add(random.nextInt(questionList1.size()));
//                }
//                questions.addAll(set.stream().map(questionList1::get).collect(Collectors.toList()));
//            }
//            template.setQuestions(questions);
//        }
//    }
