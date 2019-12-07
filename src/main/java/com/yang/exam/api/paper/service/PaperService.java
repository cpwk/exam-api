package com.yang.exam.api.paper.service;

import com.yang.exam.api.paper.model.Paper;
import com.yang.exam.api.paper.qo.PaperQo;
import org.springframework.data.domain.Page;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/3 11:43
 * @Version：1.0
 */
public interface PaperService {

    void save(Paper paper) throws Exception;

    Page<Paper> paper_list(PaperQo paperQo) throws Exception;

    Paper findById(Integer id) throws Exception;

    Paper getById(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

}
