package com.yang.exam.api.user.qo;

import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/7 16:28
 * @Version：1.0
 */
public class UserQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status = 0;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status == 0 ? null : status;
    }
}

