package com.yang.exam.api.question.controller;

import com.yang.exam.api.question.model.Parameter;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/10 16:54
 * @Version：1.0
 */
@Controller
@RequestMapping("/usr/question")
public class UserQuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/generate")
    @Action(session = SessionType.NONE)
    public ModelAndView generate(String parameter) throws Exception {
        return feedback(questionService.generate(parseModel(parameter,new Parameter())));
    }

}
