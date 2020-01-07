package com.yang.exam.api.category.controller;

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
 * @Date: 2019/12/9 20:55
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/usr/category")
public class UsrCategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category")
    @Action(session = SessionType.USER)
    public ModelAndView category(boolean usr) throws Exception {
        return feedback(categoryService.categorys(usr));
    }

    @RequestMapping(value = "category_level")
    @Action(session = SessionType.USER)
    public ModelAndView category_level() throws Exception {
        return feedback(categoryService.categoryLevel());
    }
}
