package com.yang.exam.api.template.controller;

import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.template.model.Template;
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
 * @Date: 2019/11/29 11:11
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/oms/template")
public class OmsTemplateController extends BaseController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "template_list")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TEMPLATE_EDIT)
    public ModelAndView templateList(String templateQo) throws Exception {
        return feedback(templateService.templateList(parseModel(templateQo, new TemplateQo())));
    }

    @RequestMapping(value = "save")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TEMPLATE_EDIT)
    public ModelAndView save(String template) throws Exception {
        templateService.save(parseModel(template, new Template()));
        return feedback();
    }

    @RequestMapping(value = "template_id")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TEMPLATE_EDIT)
    public ModelAndView getById(Integer id) throws Exception {
        return feedback(templateService.getById(id));
    }

    @RequestMapping(value = "template_preview")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TEMPLATE_EDIT)
    public ModelAndView templateId(Integer id) throws Exception {
        return feedback(templateService.templateId(id));
    }

    @RequestMapping(value = "status")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TEMPLATE_EDIT)
    public ModelAndView status(Integer id) throws Exception {
        templateService.status(id);
        return feedback();
    }

}
