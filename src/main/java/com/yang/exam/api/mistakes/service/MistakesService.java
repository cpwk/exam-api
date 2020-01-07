package com.yang.exam.api.mistakes.service;

import com.yang.exam.api.mistakes.model.Mistakes;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:12
 * @Version：1.0
 */
public interface MistakesService {

    void save(Mistakes mistakes);

    Mistakes findByUserId(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    Mistakes mistakesList() throws Exception;

}
