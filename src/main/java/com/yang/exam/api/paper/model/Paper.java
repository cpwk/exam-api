package com.yang.exam.api.paper.model;

import com.yang.exam.api.paper.converter.QuestionsArrayConverter;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.template.model.Template;

import javax.persistence.*;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/3 11:42
 * @Version：1.0
 */

@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer templateId;
    @Column(name = "questions")
    @Convert(converter = QuestionsArrayConverter.class)
    private List<Question> questions;
    @Column
    private Long duration;
    @Column
    private Byte totalScore;
    @Column
    private Byte passingScore;
    @Column
    private Byte status;
    @Column
    private Long createdAt;
    @Transient
    private Template template;

    public Paper() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Byte getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Byte totalScore) {
        this.totalScore = totalScore;
    }

    public Byte getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Byte passingScore) {
        this.passingScore = passingScore;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

}
