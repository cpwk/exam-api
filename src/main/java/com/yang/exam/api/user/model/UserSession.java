package com.yang.exam.api.user.model;

import javax.persistence.*;

@Entity
@Table
public class UserSession {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private String token;
    @Column
    private Long signinAt;
    @Column
    private Long ExpireAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSigninAt() {
        return signinAt;
    }

    public void setSigninAt(Long signinAt) {
        this.signinAt = signinAt;
    }

    public Long getExpireAt() {
        return ExpireAt;
    }

    public void setExpireAt(Long expireAt) {
        ExpireAt = expireAt;
    }
}
