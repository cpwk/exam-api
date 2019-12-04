package com.yang.exam.api.template.model;

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
    private Byte category;
    @Column
    private Integer totalScore;
    @Column
    private Byte passingScore;
    @Column
    private Long duration;
    @Column
    private Long createdAt;
    @Column
    private Long updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public Byte getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Byte passingScore) {
        this.passingScore = passingScore;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
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

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }
}
