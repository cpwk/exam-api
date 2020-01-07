package com.yang.exam.api.usrPaper.repository;

import com.yang.exam.api.usrPaper.model.UsrPaper;
import com.yang.exam.commons.reposiotry.BaseRepository;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 19:11
 * @Version：1.0
 */
public interface UsrPaperRepository extends BaseRepository<UsrPaper, Integer> {

    List<UsrPaper> findByUserId(Integer id) throws Exception;
}
