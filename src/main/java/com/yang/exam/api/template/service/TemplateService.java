package com.yang.exam.api.template.service;

import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.qo.TemplateQo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TemplateService {

    void save(Template template) throws Exception;

    Template findById(Integer id) throws Exception;

    Template getById(Integer id) throws Exception;

    void status(Integer id) throws Exception;

    Page<Template> templateList(TemplateQo templateQo) throws Exception;

    List<Template> template() throws Exception;

    Template templateId(Integer id) throws Exception;


}
