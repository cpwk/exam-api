package com.yang.exam.api.Role.controller;

import com.yang.exam.api.Role.model.Role;
import com.yang.exam.api.Role.service.RoleService;
import com.yang.exam.api.admin.authority.AdminPermission;
import com.yang.exam.api.admin.authority.AdminPermissionVO;
import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/30 17:26
 * @Version：1.0
 */
@Controller
@RequestMapping(path = "/oms/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    //role
    @RequestMapping(value = "/save_role")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ROLE_EDIT)
    public ModelAndView save(String role) throws Exception {
        roleService.save_role(parseModel(role, new Role()));
        return feedback(null);
    }

    @RequestMapping(value = "/remove_role")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ROLE_EDIT)
    public ModelAndView remove_role(Integer id) throws Exception {
        roleService.remove_role(id);
        return feedback(null);
    }

    @RequestMapping(value = "/role")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ROLE_EDIT)
    public ModelAndView role(Integer id) throws Exception {
        return feedback(roleService.role(id));
    }

    @RequestMapping(value = "/permissions")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ROLE_EDIT)
    public ModelAndView permissions() throws Exception {
        return feedback(AdminPermissionVO.initPermissions());
    }

    @RequestMapping(value = "/roles")
    @Action(session = SessionType.ADMIN, adminPermission = AdminPermission.ROLE_EDIT)
    public ModelAndView roles() throws Exception {
        return feedback(roleService.roles(true));
    }
}
