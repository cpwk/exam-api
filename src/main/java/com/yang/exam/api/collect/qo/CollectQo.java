package com.yang.exam.api.collect.qo;

import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 19:34
 * @Version：1.0
 */
public class CollectQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "userId")
    private Integer userId = Contexts.requestUser().getId();

    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type = 0;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type == 0 ? null : type;
    }

}
