package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.entity.MistakesOptions;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.qo.MistakesQo;
import org.springframework.data.domain.Page;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:12
 * @Version：1.0
 */
public interface MistakesService {

    void save(Mistakes mistakes);

    Page<Mistakes> mistakesList(MistakesQo qo, MistakesOptions options) throws Exception;

    Mistakes findById(Integer id) throws Exception;

    Mistakes getById(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    Mistakes findByUserIdAndQuestionId(Integer userId, Integer questionId) throws Exception;
}
