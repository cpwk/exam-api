package com.yang.exam.commons.support.model;

public class VCode {

    private Long key;
    private String code;
    private Integer accountType; //mobile=1;email=2
    private String account;

    public VCode() {
    }

    public VCode(String code) {
        this.code = code;
    }

    public VCode(Long key, String code, Integer accountType, String account) {
        this.key = key;
        this.code = code;
        this.accountType = accountType;
        this.account = account;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
