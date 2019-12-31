package com.yang.exam.api.Role.service;

import com.yang.exam.api.Role.model.Role;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/30 17:28
 * @Version：1.0
 */
public interface RoleService {

    //role
    void save_role(Role role);

    void remove_role(int id);

    Role role(Integer id) throws Exception;

    List<Role> roles(boolean init);

    Role findById(Integer id) throws Exception;

    Role getById(Integer id) throws Exception;

}
