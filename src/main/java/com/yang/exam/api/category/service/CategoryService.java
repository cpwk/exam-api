package com.yang.exam.api.category.service;

import com.yang.exam.api.category.model.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category) throws Exception;

    Category findById(Integer id) throws Exception;

    Category getById(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    List<Category> course(Integer id) throws Exception;

    List<Category> category_list() throws Exception;
}
