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

    @QueryField(type = QueryType.EQUAL, name = "categoryId")
    private Byte categoryId;

    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type = 0;

    @QueryField(type = QueryType.EQUAL, name = "difficulty")
    private Byte difficulty = 0;

    @QueryField(type = QueryType.FULL_LIKE, name = "topic")
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type == 0 ? null : type;
    }

    public Byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Byte categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
        this.difficulty = difficulty == 0 ? null : difficulty;
    }
}
