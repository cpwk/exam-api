package com.yang.exam.api.mistakes.model;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.commons.converter.IntegerArrayConverter;

import javax.persistence.*;
import java.util.List;

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
    @Convert(converter = IntegerArrayConverter.class)
    private List<Integer> questionIds;
    @Transient
    private List<Question> questions;

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

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
