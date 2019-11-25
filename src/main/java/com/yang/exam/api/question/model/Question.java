package com.yang.exam.api.question.model;

import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.commons.converter.IntegerArrayConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-21 16:55
 * @Version：1.0
 */

@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column//题目
    private String topic;
    @Column
    private Integer categoryId;
    @Column
    private Integer type;
    @Column
    private String answer;
    @Column
    private String difficulty;
    @Convert(converter = IntegerArrayConverter.class)
    @Column
    private List<Integer> tagsId;
    @Column
    private Long createdAt;
    @Column
    private Long updatedAt;
    @Transient
    private List<Tag> tag;

    public Question() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Integer> getTagsId() {
        return tagsId;
    }

    public void setTagsId(List<Integer> tagsId) {
        this.tagsId = tagsId;
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

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }
}
