package com.yang.exam.api.mistakes.model;

import javax.persistence.*;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:11
 * @Version：1.0
 */
@Entity
@Table(name = "mistakes")
public class Mistakes {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private Integer questionId;

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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
