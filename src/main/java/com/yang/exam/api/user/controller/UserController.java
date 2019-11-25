package com.yang.exam.api.user.controller;

import com.yang.exam.api.support.model.VCode;
import com.yang.exam.api.user.model.User;
import com.yang.exam.api.user.service.UserService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 19:59
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signIn")
    @Action(session = SessionType.NONE)
    public ModelAndView signIn(String user, String vCode) throws Exception {
        userService.signIn(parseModel(user, new User()), parseModel(vCode, new VCode()));
        return feedback();
    }

}
