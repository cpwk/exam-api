package com.yang.exam.api.questionTag.model;

import javax.persistence.*;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 17:02
 * @Version：1.0
 */

@Entity
@Table(name = "question_tag")
public class QuestionTag {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer tagId;
    @Column
    private Integer questionId;

    public QuestionTag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
