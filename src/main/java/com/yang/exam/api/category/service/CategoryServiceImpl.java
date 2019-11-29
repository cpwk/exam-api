package com.yang.exam.api.category.service;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.model.CategoryError;
import com.yang.exam.api.category.qo.CategoryQo;
import com.yang.exam.api.category.repository.CategoryRepository;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 14:32
 * @Version：1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService, CategoryError {

    private static final int FATHER = 0;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> category_list(CategoryQo qo) throws Exception {
        return categoryRepository.findAll(qo);
    }

    @Override
    public void save(Category category) throws Exception {
        if (category.getId() == null) {
            category.setCreatedAt(System.currentTimeMillis());
            category.setUpdatedAt(System.currentTimeMillis());
        } else {
            category.setUpdatedAt(System.currentTimeMillis());
        }
        if (StringUtils.isEmpty(category.getName())
                && category.getpId() == null
                && category.getPriority() == null
                && category.getStatus() == null) {
            throw new ServiceException(ERR_COMPLETE_EMPTY);
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

    @Override
    public List<Category> father() throws Exception {
        List<Category> category = categoryRepository.findAll();
        List<Category> cat = new ArrayList<>();
        for (Category v : category) {
            if (v.getpId().equals(FATHER)) {
                cat.add(v);
            }
            List<Category> cate = new ArrayList<>();
            for (Category va : category) {
                if (v.getId().equals(va.getpId())) {
                    cate.add(va);
                }
                List<Category> categ = new ArrayList<>();
                for (Category val : category) {
                    if (va.getId().equals(val.getpId())) {
                        categ.add(val);
                        va.setChildren(categ);
                    }
                }
                v.setChildren(cate);
            }
        }
        return cat;
    }
}


//    @Override
//    public List<Category> father() throws Exception {
//        List<Category> category = categoryRepository.findAll();
//        List<Category> cat = new ArrayList<>();
//        for (Category v : category) {
//            if (v.getpId().equals(FATHER)) {
//                cat.add(v);
//            }
//            List<Category> cate = new ArrayList<>();
//            for (Category va : category) {
//                if (v.getId().equals(va.getpId())) {
//                    cate.add(va);
//                }
//                List<Category> categ = new ArrayList<>();
//                for (Category val : category) {
//                    if (va.getId().equals(val.getpId())) {
//                        categ.add(val);
//                        va.setChildren(categ);
//                    }
//                }
//                v.setChildren(cate);
//            }
//        }
//        return cat;
//    }