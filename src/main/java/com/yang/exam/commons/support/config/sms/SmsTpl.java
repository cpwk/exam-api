package com.yang.exam.commons.support.config.sms;

public class SmsTpl {

    public static final String tpl_reg = "SMS_181865273";
    //ok
    public static final String tpl_mod_pwd = "SMS_181865273";
    public static final String tpl_file_reject = "SMS_181865273";
    public static final String tpl_formal = "SMS_181865273";


    private String templateCode;
    private String signName = "飞奔的跑跑";
    private String phoneNumbers;
    private String templateParam;

    public SmsTpl() {
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
