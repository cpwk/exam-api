package com.yang.exam.api.usrPaper.controller;

import com.yang.exam.api.usrPaper.model.UsrPaper;
import com.yang.exam.api.usrPaper.qo.UsrPaperQo;
import com.yang.exam.api.usrPaper.service.UsrPaperService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
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


    //作用随机条件生成
    @RequestMapping(value = "question")
    @Action(session = SessionType.USER)
    public ModelAndView questions(String usrPaper) throws Exception {
        return feedback(usrPaperService.questions(parseModel(usrPaper, new UsrPaper())));
    }


    //作用记录查询
    @RequestMapping(value = "record")
    @Action(session = SessionType.USER)
    public ModelAndView record(String usrPaperQo) throws Exception {
        return feedback(usrPaperService.record(parseModel(usrPaperQo, new UsrPaperQo())));
    }

    //判断收藏
    @RequestMapping(value = "usrPaper_id")
    @Action(session = SessionType.USER)
    public ModelAndView usrPaperId(Integer id) throws Exception {
        return feedback(usrPaperService.usrPaperId(id));
    }


    //开始接口不返回答案
    @RequestMapping(value = "start")
    @Action(session = SessionType.NONE)
    public ModelAndView start(Integer id) throws Exception {
        return feedback(usrPaperService.start(id));
    }

    //结束接口返回答案
    @RequestMapping(value = "end")
    @Action(session = SessionType.USER)
    public ModelAndView end(String usrPaper) throws Exception {
        return feedback(usrPaperService.end(parseModel(usrPaper, new UsrPaper())));
    }

    //修改试卷状态用户不可见
    @RequestMapping(value = "status")
    @Action(session = SessionType.USER)
    public ModelAndView status(Integer id) throws Exception {
        usrPaperService.status(id);
        return feedback();
    }


    //生成考试试卷
    @RequestMapping(value = "saveMockExam")
    @Action(session = SessionType.USER)
    public ModelAndView saveMockExam(Integer templateId) throws Exception {
        return feedback(usrPaperService.saveMockExam(templateId));
    }

}
