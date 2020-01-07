package com.yang.exam.api.category.controller;

import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.service.CategoryService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 14:31
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/oms/category")
public class OmsCategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/save")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.CATEGORY_EDIT)
    public ModelAndView save(String category) throws Exception {
        categoryService.save(parseModel(category, new Category()));
        return feedback();
    }

    @RequestMapping(value = "/category_id")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.CATEGORY_EDIT)
    public ModelAndView getById(Integer id) throws Exception {
        return feedback(categoryService.getById(id));
    }

    @RequestMapping(value = "/status")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.CATEGORY_EDIT)
    public ModelAndView status(Integer id, Byte status) throws Exception {
        categoryService.status(id, status);
        return feedback();
    }

    @RequestMapping(value = "/categorys")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.CATEGORY_EDIT)
    public ModelAndView categorys(boolean oms) {
        return feedback(categoryService.categorys(oms));
    }

    @RequestMapping(value = "/remove")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.CATEGORY_EDIT)
    public ModelAndView removeExamQuestionType(Integer id) throws Exception {
        categoryService.remove(id);
        return feedback(null);
    }

}
