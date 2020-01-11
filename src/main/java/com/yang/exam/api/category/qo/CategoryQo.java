package com.yang.exam.api.category.qo;

import com.yang.exam.commons.reposiotry.support.DataQueryObjectSort;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-26 01:20
 * @Version：1.0
 */
public class CategoryQo extends DataQueryObjectSort {

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status = 1;

    @QueryField(type = QueryType.FULL_LIKE, name = "name")
    private String name;

    public CategoryQo() {

    }

    public CategoryQo(byte status) {
        this.setStatus(status);
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status == 0 ? null : status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
