package com.yang.exam.api.collect.service;

import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.api.collect.qo.CollectQo;
import com.yang.exam.api.question.model.Question;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 19:01
 * @Version：1.0
 */
public interface CollectService {

    void save(Collect collect) throws Exception;

    void status(Integer id) throws Exception;

    Collect findById(Integer id) throws Exception;

    Collect getById(Integer id) throws Exception;

    List<Question> collectList(CollectQo collectQo) throws Exception;

}
