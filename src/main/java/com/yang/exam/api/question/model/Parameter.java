package com.yang.exam.api.question.model;

import com.yang.exam.api.template.converter.TemplateContentArrayConverter;
import com.yang.exam.api.template.entity.TemplateContent;

import javax.persistence.Convert;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/10 16:48
 * @Version：1.0
 */
public class Parameter {
    //    private List<Byte> type;
    @Convert(converter = TemplateContentArrayConverter.class)
    private List<TemplateContent> content;
    private Byte difficulty;
    private Integer categoryId;

    public List<TemplateContent> getContent() {
        return content;
    }

    public void setContent(List<TemplateContent> content) {
        this.content = content;
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
}
