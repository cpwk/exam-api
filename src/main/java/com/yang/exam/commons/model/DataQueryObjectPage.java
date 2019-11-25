package com.yang.exam.commons.model;

/**
 * @program: news-api
 * @description:
 * @author: 21936944@qq.com(ilike)
 * @create: 2019-08-15 19:28
 **/
public class DataQueryObjectPage extends DataQueryObjectSort {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int PAGE_NUMBER_MIN = 0;

    protected Integer pageNumber;
    protected Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber < PAGE_NUMBER_MIN) {
            pageNumber = PAGE_NUMBER_MIN;
        } else {
            pageNumber--;
        }
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }
}
