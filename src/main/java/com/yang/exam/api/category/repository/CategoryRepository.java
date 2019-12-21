package com.yang.exam.api.category.repository;

import com.yang.exam.api.category.model.Category;
import com.yang.exam.commons.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryRepository extends BaseRepository<com.yang.exam.api.category.model.Category, Integer> {

    Category findBySequence(String sequence);

    List<Category> findByPId(Integer pId);

    //common

    @Transactional
    @Modifying
    @Query("update Category set status= :status where sequence like CONCAT(:sequence,'%')")
    void status(byte status, String sequence);
}
