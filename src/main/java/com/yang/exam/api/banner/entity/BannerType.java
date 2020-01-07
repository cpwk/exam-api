package com.yang.exam.api.banner.entity;

public enum BannerType {

    HOME_PC(1, "PC首页"), SIGNUP(2, "PC 注册");
//    JAVA_PRY_PC(3, "PC Java就业班"), JAVA_PRY_WX(4, "微信 Java就业班"),
//    JAVA_ADV_PC(5, "PC Java高阶班"), JAVA_ADV_WX(6, "微信 Java高阶班"),
//    SERVICE_PC(7, "PC 教学服务"), SERVICE_WX(8, "微信 教学服务"),
//    REACT_PC(9, "PC React"), REACT_WX(10, "微信 React"),
//    ABOUT_PC(11, "PC 关于"), ABOUT_WX(12, "微信 关于");

    private int key;
    private String val;

    BannerType(int key, String val) {
        this.setKey(key);
        this.setVal(val);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
