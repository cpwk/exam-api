package com.yang.exam.api.category.service;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.qo.CategoryQo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    void save(Category category) throws Exception;

    Category findById(Integer id) throws Exception;

    Category getById(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    List<Category> course(Integer id) throws Exception;

    List<Category> father() throws Exception;

    Page<Category> category_list(CategoryQo qo) throws Exception;
}
