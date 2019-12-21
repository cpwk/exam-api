package com.yang.exam.api.usrPaper.controller;

import com.yang.exam.api.usrPaper.model.UsrPaper;
import com.yang.exam.api.usrPaper.qo.UsrPaperQo;
import com.yang.exam.api.usrPaper.service.UsrPaperService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 19:10
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/usr/usrPaper")
public class UsrPaperController extends BaseController {

    @Autowired
    private UsrPaperService usrPaperService;

    @RequestMapping(value = "save")
    @Action(session = SessionType.USER)
    public ModelAndView save(String usrPaper) throws Exception {
        usrPaperService.save(parseModel(usrPaper, new UsrPaper()));
        return feedback();
    }

    @RequestMapping(value = "question")
    @Action(session = SessionType.USER)
    public ModelAndView questions(String usrPaper) throws Exception {
        return feedback(usrPaperService.questions(parseModel(usrPaper, new UsrPaper())));
    }

    @RequestMapping(value = "record")
    @Action(session = SessionType.USER)
    public ModelAndView record(String usrPaperQo) throws Exception {
        return feedback(usrPaperService.record(parseModel(usrPaperQo, new UsrPaperQo())));
    }


    @RequestMapping(value = "usrPaper_id")
    @Action(session = SessionType.USER)
    public ModelAndView usrPaperId(Integer id) throws Exception {
        return feedback(usrPaperService.usrPaperId(id));
    }

    @RequestMapping(value = "status")
    @Action(session = SessionType.USER)
    public ModelAndView status(Integer id) throws Exception {
        usrPaperService.status(id);
        return feedback();
    }

}
