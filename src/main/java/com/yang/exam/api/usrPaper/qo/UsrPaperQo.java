package com.yang.exam.api.usrPaper.qo;

import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 14:39
 * @Version：1.0
 */
public class UsrPaperQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "userId")
    private Integer userId = Contexts.requestUser().getId();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type = 0;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type == 0 ? null : type;
    }

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status = 1;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status == 1 ? null : status;
    }
}
