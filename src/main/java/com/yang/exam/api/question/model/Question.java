package com.yang.exam.api.question.model;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.tag.model.Tag;
import com.yang.exam.commons.converter.IntegerArrayConverter;
import com.yang.exam.commons.converter.StringArrayConverter;

import javax.persistence.*;
import java.util.List;

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
    private Byte type;
    @Column
    private String answer;
    @Column
    private Byte difficulty;
    @Convert(converter = IntegerArrayConverter.class)
    @Column
    private List<Integer> tagsId;
    @Column
    private Byte status;
    @Convert(converter = StringArrayConverter.class)
    @Column
    private List<String> options;
    @Column
    private Long createdAt;
    @Column
    private Long updatedAt;
    @Column
    private String userAnswer;
    @Transient
    private List<Tag> tag;
    @Transient
    private Category category;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
