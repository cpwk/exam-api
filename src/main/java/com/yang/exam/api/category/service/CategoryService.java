package com.yang.exam.api.category.service;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.qo.CategoryQo;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    void save(Category category) throws Exception;

    void status(Integer id,Byte status) throws Exception;

    List<Category> categoryLevel() throws Exception;

    Category category(int id) throws Exception;

    List<Category> categorys(boolean adm);

    void remove(int id) throws Exception;

    Category findById(Integer id) throws Exception;

    Category getById(Integer id) throws Exception;

}


//    List<Category> category() throws ServiceException;

//    List<Category> category_list(CategoryQo qo) throws ServiceException;
