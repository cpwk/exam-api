package com.yang.exam.api.mistakes.resitpory;

import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.commons.reposiotry.BaseRepository;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/17 20:11
 * @Version：1.0
 */
public interface MistakesResitpory extends BaseRepository<Mistakes, Integer> {

    Mistakes findByUserId(Integer userId);

}
