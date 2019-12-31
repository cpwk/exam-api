package com.yang.exam.api.question.controller;

import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.question.entity.QuestionOptions;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.qo.QuestionQo;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
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
public class OmsQuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/save")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.QUESTION_EDIT)
    public ModelAndView save(String question) throws Exception {
        questionService.save(parseModel(question, new Question()));
        return feedback();
    }

    @RequestMapping(value = "/status")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.QUESTION_EDIT)
    public ModelAndView status(Integer id) throws Exception {
        questionService.status(id);
        return feedback();
    }

    @RequestMapping(value = "/question")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.QUESTION_EDIT)
    public ModelAndView question(Integer id) throws Exception {
        return feedback(questionService.getById(id));
    }

    @RequestMapping(value = "question_list")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.QUESTION_EDIT)
    public ModelAndView questionList(String questionQo) throws Exception {
        return feedback(questionService.questionList(parseModel(questionQo, new QuestionQo()), QuestionOptions.getOmsListInstance()));
    }


}
