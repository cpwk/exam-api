package com.yang.exam.api.mistakes.qo;

import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:50
 * @Version：1.0
 */
public class MistakesQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "userId")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
