package com.yang.exam.api.paper.service;

import com.yang.exam.api.paper.model.Paper;
import com.yang.exam.api.paper.model.PaperError;
import com.yang.exam.api.paper.qo.PaperQo;
import com.yang.exam.api.paper.resitpory.PaperResitpory;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.service.TemplateService;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (StringUtils.isEmpty(paper.getName()) ||
                paper.getPassingScore() == null ||
                paper.getDuration() == null ||
                paper.getTemplateId() == null ||
                paper.getStatus() == null ||
                paper.getTotalScore() == null) {
            throw new ServiceException(ERR_PAPER_EMPTY);
        }
        if (paper.getId() == null) {
            paper.setCreatedAt(System.currentTimeMillis());
        }
        paperResitpory.save(paper);
    }

    @Override
    public Page<Paper> paper_list(PaperQo paperQo) throws Exception {
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
}
