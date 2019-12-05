package com.yang.exam.api.template.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.Column;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/29 11:18
 * @Version：1.0
 */

@Controller
public class TemplateContent {

    @Column
    private Byte type;
    @Column
    private Integer number;
    @Column
    private Integer score;

    public TemplateContent() {
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
