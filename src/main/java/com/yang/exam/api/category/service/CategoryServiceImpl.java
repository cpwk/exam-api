package com.yang.exam.api.category.service;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.repository.CategoryRepository;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yang.exam.commons.exception.ErrorCode.ERR_DATA_NOT_FOUND;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 14:32
 * @Version：1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> category_list() throws Exception {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) throws Exception {
        if (category.getId() == null) {
            category.setCreatedAt(System.currentTimeMillis());
            category.setUpdatedAt(System.currentTimeMillis());
        } else {
            category.setUpdatedAt(System.currentTimeMillis());
        }
        categoryRepository.save(category);
        this.categoryRepository.save(category);
    }

    @Override
    public Category findById(Integer id) throws Exception {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getById(Integer id) throws Exception {
        Category category = findById(id);
        if (findById(id) == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return category;
    }


    @Override
    public void delete(Integer id) throws Exception {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> course(Integer id) throws Exception {
        return categoryRepository.findByPId(id);
    }

}
