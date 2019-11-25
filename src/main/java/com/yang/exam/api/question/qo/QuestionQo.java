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

    @QueryField(type = QueryType.FULL_LIKE,name = "topic")
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
