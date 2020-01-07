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

    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type = 0;

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status = 0;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status == 0 ? null : status;
    }
}
