package com.yang.exam.api.category.service;

import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.ListModelConverter;
import com.yang.exam.api.category.entity.CategoryError;
import com.yang.exam.api.category.model.Category;
import com.yang.exam.api.category.qo.CategoryQo;
import com.yang.exam.api.category.repository.CategoryRepository;
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.entity.Constants;
import com.yang.exam.commons.exception.RepositoryException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: yangchengcheng
 * @Date: 2019-11-22 14:32
 * @Version：1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService, CategoryError {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private KvCacheFactory kvCacheFactory;
    private KvCacheWrap<Byte, List<Category>> categoryCache;

    @PostConstruct
    public void init() {
        categoryCache = kvCacheFactory.create(new CacheOptions("category", 1, Constants.CACHE_REDIS_EXPIRE), new RepositoryProvider<Byte, List<Category>>() {
            @Override
            public List<Category> findByKey(Byte key) throws RepositoryException {
                if (key == Constants.STATUS_OK) {
                    return sortExamQuestionTypes(categoryRepository.findAll(new CategoryQo()));
                } else {
                    return sortExamQuestionTypes(categoryRepository.findAll(new CategoryQo((byte) 0)));
                }
            }

            @Override
            public Map<Byte, List<Category>> findByKeys(Collection<Byte> keys) throws RepositoryException {
                throw new UnsupportedOperationException("keys");
            }

        }, new ListModelConverter<>(Category.class));
    }

    @Override
    public void save(Category category) throws Exception {

        validateExamCategory(category);

        Integer id = category.getId();
        if (id == null || id == 0) {
            category.setCreatedAt(System.currentTimeMillis());
            category.setUpdatedAt(System.currentTimeMillis());
            categoryRepository.save(category);
            clearExamQuestionTypes();
        } else {
            Category of = category(id);
            of.setSequence(category.getSequence());
            of.setName(category.getName());
            of.setpId(category.getpId());
            of.setPriority(category.getPriority());
            of.setUpdatedAt(System.currentTimeMillis());
            categoryRepository.save(of);
            clearExamQuestionTypes();
        }
    }

    @Override
    public void status(Integer id, Byte status) throws Exception {
        if (!(status == Constants.STATUS_OK || status == Constants.STATUS_HALT)) {
            throw new ServiceException(ERR_PERMISSION_DENIED);
        }
        Category category = findById(id);
        if (category != null) {
            String sequence = category.getSequence();
            if (sequence.endsWith("0000")) {
                sequence = sequence.substring(0, 2);
            } else if (sequence.endsWith("00")) {
                sequence = sequence.substring(0, 4);
            }
            categoryRepository.status(status, sequence);
            clearExamQuestionTypes();
        }
    }

    @Override
    public List<Category> categoryLevel() throws Exception {
        return categoryRepository.findAllByStatus(Constants.STATUS_OK);
    }

    @Override
    public List<Category> categorys(boolean oms) {
        List<Category> s = categoryCache.findByKey(oms ? 0 : Constants.STATUS_OK);
        return s;
    }

    @Override
    public Category category(int id) throws Exception {
        Category category = findById(id);
        if (category == null || category.getId() == null) {
            throw new ServiceException(ERR_UNKNOWN);
        }
        return category;
    }

    @Override
    public void remove(int id) throws Exception {
        categoryRepository.deleteById(id);
        clearExamQuestionTypes();
    }

    @Override
    public Map<Integer, Category> findByids(Collection<Integer> ids) throws Exception {
        Map<Integer, Category> categoryMap = new HashMap<>();
        List<Category> categories = categoryRepository.findAllById(ids);
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
        }
        return categoryMap;
    }

    @Override
    public Category findById(Integer id) throws Exception {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getById(Integer id) throws Exception {
        Category category = findById(id);
        if (category == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return category;
    }

    private void clearExamQuestionTypes() {
        categoryCache.remove((byte) 0);
        categoryCache.remove(Constants.STATUS_OK);
    }

    private List<Category> sortExamQuestionTypes(List<Category> list) {

        List<Category> result = list.stream().filter((Category t) -> t.getpId() == 0).collect(Collectors.toList());
        for (Category v : result) {
            List<Category> list2 = list.stream().filter((Category t) -> t.getpId().intValue() == v.getId().intValue()).collect(Collectors.toList());
            for (Category va : list2) {
                List<Category> list3 = list.stream().filter((Category t) -> t.getpId().intValue() == va.getId().intValue()).collect(Collectors.toList());
                va.setChildren(list3);
            }
            v.setChildren(list2);
        }
        return result;
    }

    private void validateExamCategory(Category category) throws Exception {

        int pId = category.getpId();
        if (StringUtils.isEmpty(category.getName())) {
            throw new ServiceException(ERR_CATEGORY_NAME_EMPTY);
        }
        String sequence = category.getSequence();
        if (pId > 0) {
            Category parent = category(pId);
            int _pId = parent.getpId();
            if (_pId > 0) {
                Category grandPa = category(_pId);
                System.out.println(parent.getSequence());
                System.out.println(grandPa.getSequence());
                if (!(sequence.substring(0, 2).equals(grandPa.getSequence().substring(0, 2))) && (sequence.substring(0, 4).equals(parent.getSequence().substring(0, 4)))) {
                    throw new ServiceException(ERR_UNKNOWN);
                }
            } else {
                if (!sequence.substring(0, 2).equals(parent.getSequence().substring(0, 2))) {
                    throw new ServiceException(ERR_UNKNOWN);
                }
            }
        }
        if (category.getStatus() == 0) {
            category.setStatus(Constants.STATUS_HALT);
        }
        if (category.getPriority() == 0) {
            category.setPriority(1);
        }
    }
}
