package com.yang.exam.api.template.model;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.template.converter.TemplateContentArrayConverter;
import com.yang.exam.api.template.entity.TemplateContent;

import javax.persistence.*;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/29 11:11
 * @Version：1.0
 */

@Entity
@Table(name = "template")
public class Template {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String templateName;
    @Column
    @Convert(converter = TemplateContentArrayConverter.class)
    private List<TemplateContent> content;
    @Column
    private Byte status;
    @Column
    private Byte difficulty;
    @Column
    private Integer categoryId;
    @Column
    private Integer totalScore;
    @Column
    private Integer passingScore;
    @Column
    private Long duration;
    @Column
    private Long createdAt;
    @Column
    private Long updatedAt;
    @Transient
    private Category category;
    @Transient
    private List<Question> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<TemplateContent> getContent() {
        return content;
    }

    public void setContent(List<TemplateContent> content) {
        this.content = content;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Integer passingScore) {
        this.passingScore = passingScore;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
