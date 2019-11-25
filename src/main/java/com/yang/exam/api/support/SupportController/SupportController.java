package com.yang.exam.api.support.SupportController;

import com.yang.exam.api.support.SupportService.SupportService;
import com.yang.exam.api.support.model.VCode;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
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
