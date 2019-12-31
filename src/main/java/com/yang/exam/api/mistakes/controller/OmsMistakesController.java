package com.yang.exam.api.mistakes.controller;

import com.yang.exam.api.mistakes.model.Mistakes;
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
 * @Date: 2019/12/17 20:11
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/oms/mistakes")
public class OmsMistakesController extends BaseController {

    @Autowired
    private MistakesService mistakesService;

    @RequestMapping(value = "save")
    @Action(session = SessionType.ADMIN)
    public ModelAndView save(String mistakes) throws Exception {
        mistakesService.save(parseModel(mistakes, new Mistakes()));
        return feedback();
    }

}
