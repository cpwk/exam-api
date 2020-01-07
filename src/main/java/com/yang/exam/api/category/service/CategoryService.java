package com.yang.exam.api.category.service;

import com.yang.exam.api.category.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    void save(Category category) throws Exception;

    void status(Integer id, Byte status) throws Exception;

    List<Category> categoryLevel() throws Exception;

    Category category(int id) throws Exception;

    List<Category> categorys(boolean oms);

    void remove(int id) throws Exception;

    Category findById(Integer id) throws Exception;

    Category getById(Integer id) throws Exception;

    Map<Integer, Category> findByids(Collection<Integer> ids) throws Exception;

}

