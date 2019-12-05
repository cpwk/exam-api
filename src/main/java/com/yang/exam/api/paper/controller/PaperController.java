package com.yang.exam.api.paper.controller;

import com.yang.exam.api.paper.model.Paper;
import com.yang.exam.api.paper.qo.PaperQo;
import com.yang.exam.api.paper.service.PaperService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/3 11:42
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/oms/paper")
public class PaperController extends BaseController {

    @Autowired
    private PaperService paperService;

    @RequestMapping(value = "save")
    @Action(session = SessionType.ADMIN)
    public ModelAndView save(String paper) throws Exception {
        paperService.save(parseModel(paper, new Paper()));
        return feedback();
    }

    @RequestMapping(value = "paper_list")
    @Action(session = SessionType.ADMIN)
    public ModelAndView paper_list(String paperQo) throws Exception {
        return feedback(paperService.paper_list(parseModel(paperQo, new PaperQo())));
    }

    @RequestMapping(value = "preview")
    @Action(session = SessionType.ADMIN)
    public ModelAndView preview(Integer id) throws Exception {
        return feedback(paperService.getById(id));
    }
}
