package com.yang.exam.api.admin.authority;


import com.yang.exam.commons.entity.Constants;

public enum AdminPermission {

    //    none
    NONE("", ""),

    /* 功能模块 */
    // admin&role
    ROLE_EDIT("管理组管理", Constants.LEVEL_IMPORTANT), ADMIN_LIST("管理员列表", Constants.LEVEL_IMPORTANT), ADMIN_EDIT("编辑管理员", Constants.LEVEL_IMPORTANT),
    USER_EDIT("用户管理", Constants.LEVEL_IMPORTANT),
    //admin
    QUESTION_EDIT("试题管理", Constants.LEVEL_WARNING), TAG_EDIT("标签管理", Constants.LEVEL_WARNING), CATEGORY_EDIT("分类管理", Constants.LEVEL_WARNING),
    PAPER_EDIT("试卷管理", Constants.LEVEL_WARNING), TEMPLATE_EDIT(" 模板管理", Constants.LEVEL_WARNING),

    //setting
    BANNER_EDIT("Banner管理", Constants.LEVEL_PRIMARY);

    private String val;
    private String level;

    AdminPermission(String val, String level) {
        this.val = val;
        this.level = level;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
