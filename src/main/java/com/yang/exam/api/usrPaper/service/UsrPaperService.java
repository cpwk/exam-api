package com.yang.exam.api.usrPaper.service;

import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.usrPaper.model.UsrPaper;
import com.yang.exam.api.usrPaper.qo.UsrPaperQo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 19:11
 * @Version：1.0
 */
public interface UsrPaperService {

    UsrPaper usrPaper(int id, boolean init);

    void save(UsrPaper usrPaper) throws Exception;

    UsrPaper findById(Integer id) throws Exception;

    UsrPaper getById(Integer id) throws Exception;

    void status(Integer id) throws Exception;

    Page<UsrPaper> record(UsrPaperQo usrPaperQo) throws Exception;

    List<Question> questions(UsrPaper usrPaper) throws Exception;

    UsrPaper saveMockExam(Integer templateId) throws Exception;

    //收藏
    Map usrPaperId(Integer id) throws Exception;

    //
    Map start(Integer id) throws Exception;

    //
    Map end(UsrPaper usrPaper) throws Exception;

}
