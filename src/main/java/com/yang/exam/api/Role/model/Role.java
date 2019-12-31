package com.yang.exam.api.Role.model;


import com.yang.exam.commons.authority.Permission;
import com.yang.exam.commons.converter.StringArrayConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Convert(converter = StringArrayConverter.class)
    @Column(name = "permissions")
    private List<String> Permissions;

    @Transient
    private List<Permission> pstr;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return Permissions;
    }

    public void setPermissions(List<String> permissions) {
        Permissions = permissions;
    }

    public List<Permission> getPstr() {
        return pstr;
    }

    public void setPstr(List<Permission> pstr) {
        this.pstr = pstr;
    }
}