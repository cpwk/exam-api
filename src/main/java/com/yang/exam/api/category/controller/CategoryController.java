package com.yang.exam.api.category.controller;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.service.CategoryService;
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
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category_list")
    public ModelAndView category_list() throws Exception {
        return feedback(categoryService.category_list());
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(String category) throws Exception {
        categoryService.save(parseModel(category, new Category()));
        return feedback();
    }

    @RequestMapping(value = "/category_id")
    public ModelAndView type_id(Integer id) throws Exception {
        return feedback(categoryService.getById(id));
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(Integer id) throws Exception {
        categoryService.delete(id);
        return feedback();
    }

    @RequestMapping(value = "/course")
    public ModelAndView course(Integer id) throws Exception {
        return feedback(categoryService.course(id));
    }
}
