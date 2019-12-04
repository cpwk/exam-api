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
public class TemplateController extends BaseController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "template_list")
    @Action(session = SessionType.ADMIN)
    public ModelAndView template_list(String templateQo) throws Exception {
        return feedback(templateService.template_list(parseModel(templateQo, new TemplateQo())));
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
        return feedback(templateService.create(id));
    }

    @RequestMapping(value = "template_id")
    @Action(session = SessionType.ADMIN)
    public ModelAndView template_id(Integer id) throws Exception {
        return feedback(templateService.getById(id));
    }


    @RequestMapping(value = "delete")
    @Action(session = SessionType.ADMIN)
    public ModelAndView delete(Integer id) throws Exception {
        templateService.delete(id);
        return feedback();
    }

}
