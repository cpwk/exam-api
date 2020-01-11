package com.yang.exam.api.user.controller;

import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.user.qo.UserQo;
import com.yang.exam.api.user.service.UserService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
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
public class OmsController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "users")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.USER_EDIT)
    public ModelAndView users(String userQo) throws Exception {
        return feedback(userService.users(parseModel(userQo, new UserQo())));
    }

    @RequestMapping(value = "status")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.USER_EDIT)
    public ModelAndView status(Integer id) throws Exception {
        userService.status(id);
        return feedback();
    }

    @RequestMapping(value = "omsProfile")
    @Action(session = SessionType.NONE)
    public ModelAndView omsProfile(Integer id) throws Exception {
        return feedback(userService.omsProfile(id));
    }

}
