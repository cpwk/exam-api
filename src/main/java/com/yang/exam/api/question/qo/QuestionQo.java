package com.yang.exam.api.question.qo;

import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-25 17:08
 * @Version：1.0
 */
public class QuestionQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.FULL_LIKE, name = "topic")
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @QueryField(type = QueryType.EQUAL, name = "categoryId")
    private Byte categoryId;

    public Byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Byte categoryId) {
        this.categoryId = categoryId;
    }

    @QueryField(type = QueryType.EQUAL, name = "difficulty")
    private Byte difficulty;

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
        this.difficulty = difficulty;
    }

}