package com.yang.exam.api.banner.qo;


import com.yang.exam.commons.reposiotry.support.DataQueryObjectSort;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

public class BannerQo extends DataQueryObjectSort {

    @QueryField(type = QueryType.EQUAL, name = "type")
    private Integer type;

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Integer status = 1;

    private String sortPropertyName = "priority";

    public BannerQo() {

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status == 0 ? null : status;
    }

    @Override
    public String getSortPropertyName() {
        return sortPropertyName;
    }

    @Override
    public void setSortPropertyName(String sortPropertyName) {
        this.sortPropertyName = sortPropertyName;
    }

}
