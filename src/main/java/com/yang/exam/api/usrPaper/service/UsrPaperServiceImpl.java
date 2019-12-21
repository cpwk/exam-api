package com.yang.exam.api.usrPaper.service;

import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.api.collect.service.CollectService;
import com.yang.exam.api.mistakes.model.Mistakes;
import com.yang.exam.api.mistakes.service.MistakesService;
import com.yang.exam.api.question.model.Question;
import com.yang.exam.api.question.service.QuestionService;
import com.yang.exam.api.template.entity.TemplateContent;
import com.yang.exam.api.template.model.Template;
import com.yang.exam.api.template.service.TemplateService;
import com.yang.exam.api.usrPaper.entity.UsrPaperError;
import com.yang.exam.api.usrPaper.model.UsrPaper;
import com.yang.exam.api.usrPaper.qo.UsrPaperQo;
import com.yang.exam.api.usrPaper.repository.UserPaperRepository;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.yang.exam.commons.entity.Constants.STATUS_HALT;
import static com.yang.exam.commons.entity.Constants.STATUS_OK;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/11 19:11
 * @Version：1.0
 */
@Service
public class UsrPaperServiceImpl implements UsrPaperService, UsrPaperError {

    @Autowired
    private UserPaperRepository userPaperRepository;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private MistakesService mistakesService;

    @Autowired
    private CollectService collectService;

    @Override
    public void save(UsrPaper usrPaper) throws Exception {
        long TIME = System.currentTimeMillis() % 100000;
        if (usrPaper.getTemplateId() != null) {
            Template template = templateService.getById(usrPaper.getTemplateId());
            usrPaper.setUserId(Contexts.requestUser().getId());
            usrPaper.setPaperName(template.getTemplateName() + TIME);
            usrPaper.setDifficulty(template.getDifficulty());
            usrPaper.setCategoryId(template.getCategoryId());
            usrPaper.setStatus(STATUS_OK);
            usrPaper.setTotalTime(template.getDuration() - usrPaper.getTotalTime());
            usrPaper.setContent(template.getContent());
        }
        if (usrPaper.getPaperName() == null) {
            usrPaper.setUserId(Contexts.requestUser().getId());
            usrPaper.setStatus(STATUS_OK);
            usrPaper.setPaperName(getTime());
        }
        if (usrPaper.getCreatedAt() == null) {
            usrPaper.setCreatedAt(System.currentTimeMillis());
        }
        saveMistakes(usrPaper);
        userPaperRepository.save(usrPaper);
    }

    @Override
    public UsrPaper findById(Integer id) throws Exception {
        return userPaperRepository.findById(id).orElse(null);
    }

    @Override
    public UsrPaper getById(Integer id) throws Exception {
        UsrPaper usrPaper = findById(id);
        if (usrPaper == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return usrPaper;
    }

    @Override
    public Map usrPaperId(Integer id) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        UsrPaper usrPaper = findById(id);
        if (usrPaper == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        List<Collect> collects = collectService.findByUserId(userId);

//        for (Collect collect : collects) {
//            for (Question question : usrPaper.getQuestions()) {
//                if (collect.getQuestionId().equals(question.getId())) {
//                    question.setCollected(STATUS_OK);
//                }
//            }
//        }
//        return usrPaper;

        return CollectionUtil.arrayAsMap("usrPaper", usrPaper, "collects", collects);

    }

    @Override
    public List<Question> questions(UsrPaper usrPaper) throws Exception {
        List<Question> questionList = questionService.getAllByCategoryId(usrPaper.getCategoryId());
        Random random = new Random();
        List<Question> questions = new ArrayList<>();
        for (TemplateContent v : usrPaper.getContent()) {
            List<Question> questionList1 = new ArrayList<>();
            for (Question question : questionList) {
                if (v.getType().equals(question.getType())) {
                    questionList1.add(question);
                }
            }
            Set<Integer> set = new HashSet(v.getNumber());
            while (set.size() < v.getNumber()) {
                set.add(random.nextInt(questionList1.size()));
            }
            for (Integer integer : set) {
                questions.add(questionList1.get(integer));
            }
        }
        return questions;
    }

    private String getTime() {
        return "试卷" + System.currentTimeMillis() % 100000;
    }

    private void saveMistakes(UsrPaper usrPaper) throws Exception {
        for (Question question : usrPaper.getQuestions()) {
            Mistakes mistakes = new Mistakes();
            if (!question.getAnswer().equals(question.getUserAnswer())) {
                if (mistakesService.findByUserIdAndQuestionId(usrPaper.getUserId(), question.getId()) == null) {
                    mistakes.setUserId(Contexts.requestUser().getId());
                    mistakes.setQuestionId(question.getId());
                    mistakes.setType(question.getType());
                    mistakesService.save(mistakes);
                }
            }
        }
    }

    @Override
    public Page<UsrPaper> record(UsrPaperQo usrPaperQo) throws Exception {
        return userPaperRepository.findAll(usrPaperQo);
    }

    @Override
    public void status(Integer id) throws Exception {
        UsrPaper exist = getById(id);
        exist.setStatus(STATUS_HALT);
        save(exist);
    }
}

//        public void method(UsrPaper usrPaper,String a){
//        //
//    }
//
//    public void method(UsrPaper usrPaper){
//        method(usrPaper,"aaaa");
//    }
