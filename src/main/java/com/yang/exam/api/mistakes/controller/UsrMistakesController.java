package com.yang.exam.api.mistakes.controller;

import com.yang.exam.api.mistakes.service.MistakesService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 21:03
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/usr/mistakes")
public class UsrMistakesController extends BaseController {

    @Autowired
    private MistakesService mistakesService;

    @RequestMapping(value = "mistakes_list")
    @Action(session = SessionType.USER)
    public ModelAndView mistakesList() throws Exception {
        return feedback(mistakesService.mistakesList());
    }

    @RequestMapping(value = "delete")
    @Action(session = SessionType.USER)
    public ModelAndView delete(Integer id) throws Exception {
        mistakesService.delete(id);
        return feedback();
    }

}
