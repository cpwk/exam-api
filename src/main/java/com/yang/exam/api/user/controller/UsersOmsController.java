package com.yang.exam.api.user.controller;

import com.yang.exam.api.user.qo.UserQo;
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
 * @Date: 2019/12/7 15:18
 * @Version：1.0
 */

@Controller
@RequestMapping(path = "/oms/user")
public class UsersOmsController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "users")
    @Action(session = SessionType.ADMIN)
    public ModelAndView users(String userQo) throws Exception {
        return feedback(userService.users(parseModel(userQo, new UserQo())));
    }

    @RequestMapping(value = "status")
    @Action(session = SessionType.ADMIN)
    public ModelAndView status(Integer id) throws Exception {
        userService.status(id);
        return feedback();
    }

}