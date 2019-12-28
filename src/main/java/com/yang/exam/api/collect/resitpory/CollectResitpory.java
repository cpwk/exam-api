package com.yang.exam.api.collect.resitpory;

import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.commons.reposiotry.BaseRepository;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 19:00
 * @Version：1.0
 */
public interface CollectResitpory extends BaseRepository<Collect, Integer> {

    Collect findByUserIdAndQuestionId(Integer userId, Integer questionId) throws Exception;

    List<Collect> findByUserId(Integer userId) throws Exception;

}
