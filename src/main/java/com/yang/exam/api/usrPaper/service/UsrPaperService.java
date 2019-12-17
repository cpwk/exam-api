package com.yang.exam.api.usrPaper.service;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.usrPaper.model.UsrPaper;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 19:11
 * @Version：1.0
 */
public interface UsrPaperService {

    UsrPaper findById(Integer id) throws Exception;

    UsrPaper getById(Integer id) throws Exception;

    void save(UsrPaper usrPaper) throws Exception;

    List<Question> questions(Integer templateId) throws Exception;

    List<Question> questions(UsrPaper usrPaper) throws Exception;

}
