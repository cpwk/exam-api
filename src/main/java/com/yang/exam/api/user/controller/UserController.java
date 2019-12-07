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

@Controller
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin")
    @Action(session = SessionType.NONE)
    public ModelAndView signin(String user, String vCode) throws Exception {
        return feedback(userService.signin(parseModel(user, new User()), parseModel(vCode, new VCode())));
    }

    @RequestMapping(value = "/signup")
    @Action(session = SessionType.NONE)
    public ModelAndView signup(String user, String vCode) throws Exception {
        User model = parseModel(user, new User());
        userService.signup(model, parseModel(vCode, new VCode()));
        return feedback();
    }

    @RequestMapping(value = "/resetPassword")
    @Action(session = SessionType.NONE)
    public ModelAndView reserPassword(String user, String vCode) throws Exception {
        userService.resetPassword(parseModel(user, new User()), parseModel(vCode, new VCode()));
        return feedback();
    }

    @RequestMapping(value = "/findById")
    @Action(session = SessionType.NONE)
    public ModelAndView findById(Integer id) throws Exception{
        return feedback(userService.getById(id));
    }

    @RequestMapping(value = "/update_personal")
    @Action(session = SessionType.NONE)
    public ModelAndView update_personal(String user) throws Exception {
        return feedback(userService.update_personal(parseModel(user, new User())));
    }

}

