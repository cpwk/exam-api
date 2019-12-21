package com.yang.exam.api.mistakes.model;

import com.yang.exam.api.question.model.Question;

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
    @Column
    private Byte status;
    @Column
    private Byte type;
    @Transient
    private Question question;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
