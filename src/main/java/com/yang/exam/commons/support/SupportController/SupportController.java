package com.yang.exam.commons.support.SupportController;

import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.support.SupportService.SupportService;
import com.yang.exam.commons.support.model.VCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/send")
public class SupportController extends BaseController {


    @Autowired
    private SupportService supportService;

    @RequestMapping(value = "/file")
    @Action(session = SessionType.NONE)
    public ModelAndView sendSms(String vCode) throws Exception {
        supportService.sendSms(parseModel(vCode, new VCode()));
        return feedback();
    }


}
