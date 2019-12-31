package com.yang.exam.api.tag.controller;

import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.api.tag.service.TagService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 19:58
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/oms/tag")
public class OmsTagController extends BaseController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/save")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TAG_EDIT)
    public ModelAndView save(String tag) throws Exception {
        tagService.save(parseModel(tag, new Tag()));
        return feedback();
    }

    @RequestMapping(value = "tag")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TAG_EDIT)
    public ModelAndView tag() throws Exception {
        return feedback(tagService.findTags());
    }

    @RequestMapping(value = "/delete")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.TAG_EDIT)
    public ModelAndView delete(Integer id) throws Exception {
        tagService.delete(id);
        return feedback();
    }
}
