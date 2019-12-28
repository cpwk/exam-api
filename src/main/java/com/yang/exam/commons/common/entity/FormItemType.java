package com.yang.exam.commons.common.entity;

public enum FormItemType {

    INPUT("input", "单行输入"), TEXTAREA("textarea", "多行输入"),
    SELECT("select", "单选"), MSELECT("mselect", "多选"),
    MOBILE("mobile", "手机号"), CITY("city", "城市"),
    DATE("date", "日期"), TIP("tip", "提示");

    private String key;
    private String label;

    FormItemType(String key, String label) {
        this.setKey(key);
        this.setLabel(label);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
