package com.yang.exam.api.template.service;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.qo.TemplateQo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TemplateService {

    Page<Template> template_list(TemplateQo templateQo) throws Exception;

    List<Template> template() throws Exception;

    void save(Template template) throws Exception;

    Template findById(Integer id) throws Exception;

    Template getById(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    List<Question> create(Integer id) throws Exception;

}
