package com.yang.exam.api.paper.service;

import com.yang.exam.api.paper.entity.PaperError;
import com.yang.exam.api.paper.model.Paper;
import com.yang.exam.api.paper.qo.PaperQo;
import com.yang.exam.api.paper.resitpory.PaperResitpory;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.service.TemplateService;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/3 11:43
 * @Version：1.0
 */

@Service
public class PaperServiceImpl implements PaperService, PaperError {

    @Autowired
    private PaperResitpory paperResitpory;

    @Autowired
    private TemplateService templateService;

    @Override
    public void save(Paper paper) throws Exception {
        check(paper);
        List<Question> newList = templateService.questions(paper.getTemplateId());
        paper.setQuestions(newList);
        if (paper.getId() == null) {
            paper.setCreatedAt(System.currentTimeMillis());
        }
        paperResitpory.save(paper);

    }

    private void check(Paper paper) {
        if (StringUtils.isEmpty(paper.getName())) {
            throw new ServiceException(ERR_PAPER_NAME_EMPTY);
        }
    }

    @Override
    public Page<Paper> paperList(PaperQo paperQo) throws Exception {
        Page<Paper> papers = paperResitpory.findAll(paperQo);
        List<Template> templates = templateService.template();
        for (Paper p : papers) {
            for (Template template : templates) {
                if (p.getTemplateId().equals(template.getId())) {
                    p.setTemplate(template);
                }
            }
        }
        return papers;
    }

    @Override
    public Paper findById(Integer id) throws Exception {
        return paperResitpory.findById(id).orElse(null);
    }

    @Override
    public Paper getById(Integer id) throws Exception {
        Paper paper = findById(id);
        if (paper == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return paper;
    }

    @Override
    public void status(Integer id) throws Exception {
        Paper paper = findById(id);
        if (paper == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        if (paper.getStatus().equals(STATUS_OK)) {
            paper.setStatus(STATUS_HALT);
        } else {
            paper.setStatus(STATUS_OK);
        }
        save(paper);
    }

}
