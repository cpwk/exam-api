package com.yang.exam.api.admin.controller;

import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.admin.model.Admin;
import com.yang.exam.api.admin.qo.AdminSessionQo;
import com.yang.exam.api.admin.service.AdminService;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/oms/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    //signin
    @RequestMapping(value = "/signIn")
    @Action(session = SessionType.NONE)
    public ModelAndView signIn(String admin) throws Exception {
        return feedback(adminService.signIn(parseModel(admin, new Admin())));
    }

    @RequestMapping(value = "/save_admin")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ADMIN_EDIT)
    public ModelAndView save_admin(String admin) throws Exception {
        adminService.save_admin(parseModel(admin, new Admin()));
        return feedback(null);
    }

    @RequestMapping(value = "/admin")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ADMIN_EDIT)
    public ModelAndView admin(Integer id) throws Exception {
        return feedback(adminService.admin(id, false));
    }

    @RequestMapping(value = "/admins")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ADMIN_LIST)
    public ModelAndView admins() throws Exception {
        return feedback(adminService.admins());
    }

    @RequestMapping(value = "/adminSessions")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ADMIN_LIST)
    public ModelAndView adminSessions(String adminSessionQo) throws Exception {
        return feedback(adminService.adminSessions(parseModel(adminSessionQo, new AdminSessionQo())));
    }

    @RequestMapping(value = "/remove_admin")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ADMIN_EDIT)
    public ModelAndView admin_remove(Integer id) throws Exception {
        adminService.remove_admin(id);
        return feedback(null);
    }

    @RequestMapping(value = "/profile")
    @Action(session = SessionType.ADMIN)
    public ModelAndView profile() throws Exception {
        return feedback(adminService.profile());
    }

    @RequestMapping(value = "/update_password")
    @Action(session = SessionType.ADMIN)
    public ModelAndView updatePassword(String password, String oldPassword) throws Exception {
        adminService.update_password(password, oldPassword);
        return feedback();
    }
}
