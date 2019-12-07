package com.yang.exam.api.paper.service;

import com.yang.exam.api.category.service.CategoryService;
import com.yang.exam.api.paper.model.Paper;
import com.yang.exam.api.paper.model.PaperError;
import com.yang.exam.api.paper.qo.PaperQo;
import com.yang.exam.api.paper.resitpory.PaperResitpory;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.api.template.entity.TemplateContent;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.service.TemplateService;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/3 11:43
 * @Version：1.0
 */

@Service
public class PaperServiceImpl implements PaperService, PaperError {

    private static final byte STATUS = 2;

    @Autowired
    private PaperResitpory paperResitpory;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void save(Paper paper) throws Exception {
        check(paper);
        List<Question> newList = generate(paper);
        paper.setQuestions(newList);
        if (paper.getId() == null) {
            paper.setCreatedAt(System.currentTimeMillis());
        }
        paperResitpory.save(paper);

    }

    private void check(Paper paper) {
        if (StringUtils.isEmpty(paper.getName()) ||
                paper.getPassingScore() == null ||
                paper.getDuration() == null ||
                paper.getTemplateId() == null ||
                paper.getStatus() == null ||
                paper.getTotalScore() == null) {
            throw new ServiceException(ERR_PAPER_EMPTY);
        }
    }

    private List<Question> generate(Paper paper) throws Exception {
        Template template = templateService.findById(paper.getTemplateId());
        Random random = new Random();
        List<Question> questions = null;
        List<Question> newList = new ArrayList<>();
        if (templateService.findById(paper.getTemplateId()) != null) {
            for (TemplateContent v : template.getContent()) {
                Set<Integer> set = new HashSet(v.getNumber());
                questions = questionService.findByType(v.getType());
                while (set.size() < v.getNumber()) {
                    set.add(random.nextInt(questions.size()));
                }
                for (Integer integer : set) {
                    newList.add(questions.get(integer));
                }
            }
        }
        return newList;
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
    public void delete(Integer id) throws Exception {
        Paper paper = findById(id);
        if (paper != null) {
            paper.setStatus(STATUS);
        }
        save(paper);
    }

    //    @Override
//    public List<Question> create(Integer id) throws Exception {
//        Template template = findById(id);
//        Random random = new Random();
//        List<Question> questions = null;
//        List<Question> newList = new ArrayList<>();
//        if (findById(id) != null) {
//            for (TemplateContent v : template.getContent()) {
//                Set<Integer> set = new HashSet(v.getNumber());
//                questions = questionService.findByType(v.getType());
//                while (set.size() < v.getNumber()) {
//                    set.add(random.nextInt(questions.size()));
////                    System.out.println(questions.size());
//                }
//                for (Integer integer : set) {
//                    newList.add(questions.get(integer));
//                }
//            }
//
//        }
//        return newList;
//    }
}
