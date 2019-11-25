package com.yang.exam.api.question.controller;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.qo.QuestionQo;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 17:04
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/oms/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/save")
    public ModelAndView save(String question) throws Exception {
        questionService.save(parseModel(question, new Question()));
        return feedback();
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(Integer id) throws Exception {
        questionService.delete(id);
        return feedback();
    }

    @RequestMapping(value = "/question")
    public ModelAndView question(Integer id) throws Exception {
        return feedback(questionService.getById(id));
    }

    @RequestMapping(value = "question_list")
    public ModelAndView question_list(String questionQo) throws Exception {
        return feedback(questionService.question_list(parseModel(questionQo, new QuestionQo())));
    }


}
