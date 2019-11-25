package com.yang.exam.commons.model;

import org.springframework.data.domain.Sort;

/**
 * @program: news-api
 * @description:
 * @author: 21936944@qq.com(ilike)
 * @create: 2019-08-15 19:29
 **/
public class DataQueryObjectSort {
    protected String sortPropertyName = "id";
    protected Sort.Direction sortDirection = Sort.Direction.DESC;

    public String getSortPropertyName() {
        return sortPropertyName;
    }

    public void setSortPropertyName(String sortPropertyName) {
        this.sortPropertyName = sortPropertyName;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }
}
