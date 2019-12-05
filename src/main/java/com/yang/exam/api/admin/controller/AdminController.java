package com.yang.exam.api.admin.controller;

import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.service.AdminService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/oms/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/signIn")
    @Action(session = SessionType.NONE)
    public ModelAndView signIn(String admin) throws Exception {
        return feedback(adminService.signIn(parseModel(admin, new Admin())));
    }

}
