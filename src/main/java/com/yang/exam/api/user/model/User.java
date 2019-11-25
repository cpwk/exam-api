package com.yang.exam.api.user.model;

import javax.persistence.*;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 19:59
 * @Version：1.0
 */

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String userName;
    @Column
    private String mobile;
    @Column
    private String email;
    @Column
    private String avatar;
    @Column
    private String password;

    public User() {
    }

    public User(String name, String userName, String mobile, String email, String avatar, String password) {
        this.name = name;
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
