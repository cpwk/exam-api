package com.yang.exam.api.collect.service;

import com.yang.exam.api.collect.entity.CollectOptions;
import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.api.collect.qo.CollectQo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 19:01
 * @Version：1.0
 */
public interface CollectService {

    void collect(Collect collect) throws Exception;

    Collect findById(Integer id) throws Exception;

    Collect getById(Integer id) throws Exception;

    List<Collect> findByUserId(Integer id) throws Exception;

    Page<Collect> collectList(CollectQo collectQo, CollectOptions options) throws Exception;

}
