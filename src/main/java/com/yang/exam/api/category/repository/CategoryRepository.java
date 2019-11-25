package com.yang.exam.api.category.repository;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.commons.reposiotry.BaseRepository;

import java.util.List;

public interface CategoryRepository extends BaseRepository<com.yang.exam.api.category.model.Category, Integer> {

    com.yang.exam.api.category.model.Category findBySequence(String sequence);

    List<com.yang.exam.api.category.model.Category> findByPId(Integer pId);
}
