package com.yang.exam.api.template.controller;

import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.qo.TemplateQo;
import com.yang.exam.api.template.service.TemplateService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/29 11:11
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/oms/template")
public class OmsTemplateController extends BaseController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "template_list")
    @Action(session = SessionType.ADMIN)
    public ModelAndView templateList(String templateQo) throws Exception {
        return feedback(templateService.templateList(parseModel(templateQo, new TemplateQo())));
    }

    @RequestMapping(value = "save")
    @Action(session = SessionType.ADMIN)
    public ModelAndView save(String template) throws Exception {
        templateService.save(parseModel(template, new Template()));
        return feedback();
    }

    @RequestMapping(value = "create")
    @Action(session = SessionType.ADMIN)
    public ModelAndView create(Integer id) throws Exception {
        return feedback(templateService.questions(id));
    }

    @RequestMapping(value = "getById")
    @Action(session = SessionType.ADMIN)
    public ModelAndView getById(Integer id) throws Exception {
        return feedback(templateService.getById(id));
    }


    @RequestMapping(value = "status")
    @Action(session = SessionType.ADMIN)
    public ModelAndView status(Integer id) throws Exception {
        templateService.status(id);
        return feedback();
    }

}