package com.yang.exam.api.template.service;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.api.template.entity.TemplateContent;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.model.TemplateError;
import com.yang.exam.api.template.qo.TemplateQo;
import com.yang.exam.api.template.repository.TemplateRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/29 11:12
 * @Version：1.0
 */

@Service
public class TemplateServiceImpl implements TemplateService, TemplateError {

    private static final byte STATUS = 2;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private QuestionService questionService;

    @Override
    public Page<Template> template_list(TemplateQo templateQo) throws Exception {
        return templateRepository.findAll(templateQo);
    }

    @Override
    public List<Template> template() throws Exception {
        return templateRepository.findAll();
    }

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
    public void delete(Integer id) throws Exception {
        Template template = findById(id);
        if (findById(id) != null) {
            template.setStatus(STATUS);
        }
        save(template);
    }

    @Override
    public List<Question> create(Integer id) throws Exception {
        Template template = findById(id);
        Random random = new Random();
        List<Question> questions = null;
        List<Question> newList = new ArrayList<>();
        if (findById(id) != null) {
            for (TemplateContent v : template.getContent()) {
                Set<Integer> set = new HashSet(v.getNumber());
                questions = questionService.findByType(v.getType());
                while (set.size() < v.getNumber()) {
                    set.add(random.nextInt(questions.size()));
//                    System.out.println(questions.size());
                }
                for (Integer integer : set) {
                    newList.add(questions.get(integer));
                }
            }

        }
        return newList;
    }

    private void check(Template template) throws Exception {
        if (StringUtils.isEmpty(template.getTemplateName()) ||
                template.getContent() == null ||
                template.getDifficulty() == null ||
                template.getStatus() == null ||
                template.getTotalScore() == null ||
                template.getCategory() == null ||
                template.getDuration() == null ||
                template.getPassingScore() == null) {
            throw new ServiceException(ERR_TEMPLATE_EMPTY);
        }
    }
}
