package com.yang.exam.api.usrPaper.model;

import com.yang.exam.api.paper.converter.QuestionsArrayConverter;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.template.converter.TemplateContentArrayConverter;
import com.yang.exam.api.template.entity.TemplateContent;

import javax.persistence.*;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 19:10
 * @Version：1.0
 */
@Entity
@Table(name = "usr_paper")
public class UsrPaper {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String paperName;
    @Column
    private Integer userId;
    @Column
    @Convert(converter = QuestionsArrayConverter.class)
    private List<Question> questions;
    @Column
    private Long createdAt;
    @Column
    private Integer totalScore;
    @Column
    private Byte difficulty;
    @Column
    private Integer categoryId;
    @Column
    @Convert(converter = TemplateContentArrayConverter.class)
    private List<TemplateContent> content;
    @Column
    private Long totalTime;
    @Column
    private Byte type;
    @Column
    private Byte status;
    @Column
    private Integer templateId;

    public UsrPaper() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
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

    public List<TemplateContent> getContent() {
        return content;
    }

    public void setContent(List<TemplateContent> content) {
        this.content = content;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
