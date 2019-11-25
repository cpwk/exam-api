package com.yang.exam.api.tag.controller;

import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.api.tag.service.TagService;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 19:58
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/oms/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/save")
    public ModelAndView save(String tag) throws Exception {
        tagService.save(parseModel(tag, new Tag()));
        return feedback();
    }

    @RequestMapping(value = "tag")
    public ModelAndView tag()throws Exception{
        return feedback(tagService.tag());
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(Integer id) throws Exception {
        tagService.delete(id);
        return feedback();
    }
}
