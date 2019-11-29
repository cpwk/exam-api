package com.yang.exam.api.category.qo;

import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-26 01:20
 * @Version：1.0
 */
public class CategoryQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.FULL_LIKE,name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
