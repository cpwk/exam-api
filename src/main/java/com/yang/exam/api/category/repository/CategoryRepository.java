package com.yang.exam.api.category.repository;

import com.yang.exam.commons.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CategoryRepository extends BaseRepository<com.yang.exam.api.category.model.Category, Integer> {

    @Transactional
    @Modifying
    @Query("update Category set status= :status where sequence like CONCAT(:sequence,'%')")
    void status(byte status, String sequence);

}
