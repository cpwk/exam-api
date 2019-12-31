package com.yang.exam.api.template.controller;

import com.yang.exam.api.template.qo.TemplateQo;
import com.yang.exam.api.template.service.TemplateService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/16 11:29
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/usr/template")
public class UsrTemplateController extends BaseController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "template_list")
    @Action(session = SessionType.USER)
    public ModelAndView templateList(String templateQo) throws Exception {
        return feedback(templateService.templateList(parseModel(templateQo, new TemplateQo())));
    }

    @RequestMapping(value = "template_id")
    @Action(session = SessionType.USER)
    public ModelAndView templateId(Integer id) throws Exception {
        return feedback(templateService.templateId(id));
    }

}
