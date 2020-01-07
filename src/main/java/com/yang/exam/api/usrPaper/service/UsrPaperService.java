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
    //保存试卷
    void save(UsrPaper usrPaper) throws Exception;

    UsrPaper findById(Integer id) throws Exception;

    UsrPaper getById(Integer id) throws Exception;

    //单ID试卷总数
    Integer totalNumber(Integer id) throws Exception;

    //试卷删除，假
    void status(Integer id) throws Exception;

    //试卷记录
    Page<UsrPaper> record(UsrPaperQo usrPaperQo) throws Exception;

    //随机练习生成方法
    List<Question> questions(UsrPaper usrPaper) throws Exception;

    //生成试卷
    UsrPaper saveMockExam(Integer templateId) throws Exception;

    //收藏
    Map usrPaperId(Integer id) throws Exception;

    //开始考试
    Map start(Integer id) throws Exception;

    //结束考试
    Map end(UsrPaper usrPaper) throws Exception;

}
