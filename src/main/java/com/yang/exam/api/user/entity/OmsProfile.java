package com.yang.exam.api.user.entity;

/**
 * @author: yangchengcheng
 * @Date: 2020/1/7 14:57
 * @Version：1.0
 */
public class OmsProfile {
    private String name;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String avatar;
    private Byte status;
    private Byte sex;
    private Long signupAt;
    private Integer totalUsrPaper;
    private Integer totalCollect;
    private Integer totalMistakes;
    private UserSession userSession;

    public OmsProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Long getSignupAt() {
        return signupAt;
    }

    public void setSignupAt(Long signupAt) {
        this.signupAt = signupAt;
    }

    public Integer getTotalUsrPaper() {
        return totalUsrPaper;
    }

    public void setTotalUsrPaper(Integer totalUsrPaper) {
        this.totalUsrPaper = totalUsrPaper;
    }

    public Integer getTotalCollect() {
        return totalCollect;
    }

    public void setTotalCollect(Integer totalCollect) {
        this.totalCollect = totalCollect;
    }

    public Integer getTotalMistakes() {
        return totalMistakes;
    }

    public void setTotalMistakes(Integer totalMistakes) {
        this.totalMistakes = totalMistakes;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}
