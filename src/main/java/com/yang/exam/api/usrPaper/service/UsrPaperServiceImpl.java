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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yang.exam.commons.entity.Constants.*;

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
        usrPaper.setUserId(Contexts.requestUser().getId());
        usrPaper.setStatus(STATUS_OK);
        usrPaper.setPaperName(getTime());
        usrPaper.setCreatedAt(System.currentTimeMillis());
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
    public void status(Integer id) throws Exception {
        UsrPaper exist = getById(id);
        exist.setStatus(STATUS_HALT);
        save(exist);
    }

    @Override
    public Page<UsrPaper> record(UsrPaperQo usrPaperQo) throws Exception {
        return userPaperRepository.findAll(usrPaperQo);
    }

    @Override
    public List<Question> questions(UsrPaper usrPaper) throws Exception {
        List<Integer> questionsIds = new ArrayList<>();
        for (TemplateContent v : usrPaper.getContent()) {
            questionsIds.addAll(questionService.randomQuestionList(usrPaper.getCategoryId(), usrPaper.getDifficulty(), STATUS_OK, v.getType(), v.getNumber()));
        }
        List<Question> questions = new ArrayList<>();
        for (Integer questionsId : questionsIds) {
            questions.add(questionService.getById(questionsId));
        }
        return questions;
    }

    @Override
    public UsrPaper saveMockExam(Integer templateId) throws Exception {
        return saveUsrPaper(templateId);
    }

    @Override
    public Map start(Integer id) throws Exception {
        UsrPaper usrPaper = getById(id);
        Template template = templateService.getById(usrPaper.getTemplateId());
        if (usrPaper.getType() == 0) {
            for (Question question : usrPaper.getQuestions()) {
                question.setAnswer(null);
            }
        } else {
            return CollectionUtil.arrayAsMap("usrPaper", usrPaper, "template", template);
        }
        return CollectionUtil.arrayAsMap("usrPaper", usrPaper, "template", template);
    }

    @Override
    public Map end(UsrPaper usrPaper) throws Exception {
        UsrPaper exist = getById(usrPaper.getId());
        Template template = templateService.getById(usrPaper.getTemplateId());
        Long TIME = template.getDuration() - usrPaper.getTotalTime();
        addAnswer(usrPaper, exist);
        exist.setType(usrPaper.getType());
        exist.setTotalTime(TIME);
        save(exist);
        saveMistakes(exist);
        return CollectionUtil.arrayAsMap("usrPaper", exist, "template", template);
    }

    @Override
    public Map usrPaperId(Integer id) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        UsrPaper usrPaper = getById(id);
        List<Collect> collects = collectService.findByUserId(userId);
        return CollectionUtil.arrayAsMap("usrPaper", usrPaper, "collects", collects);

    }

    private String getTime() {
        return "试卷" + System.currentTimeMillis() % 100000;
    }

    private void saveMistakes(UsrPaper usrPaper) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        for (Question question : usrPaper.getQuestions()) {
            Mistakes mistakes = new Mistakes();
            if (!question.getAnswer().equals(question.getUserAnswer())) {
                if (mistakesService.findByUserIdAndQuestionId(userId, question.getId()) == null) {
                    mistakes.setUserId(userId);
                    mistakes.setQuestionId(question.getId());
                    mistakes.setType(question.getType());
                    mistakesService.save(mistakes);
                }
            }
        }
    }

    private UsrPaper saveUsrPaper(Integer templateId) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        long NAME = System.currentTimeMillis() % 100000;
        Template template = templateService.templateId(templateId);
        UsrPaper usrPaper = new UsrPaper();
        if (template != null) {
            usrPaper.setStatus(STATUS_OK);
            usrPaper.setDifficulty(template.getDifficulty());
            usrPaper.setPaperName(template.getTemplateName() + NAME);
            usrPaper.setCategoryId(template.getCategoryId());
            usrPaper.setUserId(userId);
            usrPaper.setQuestions(template.getQuestions());
            usrPaper.setTemplateId(template.getId());
            usrPaper.setCreatedAt(System.currentTimeMillis());
            usrPaper.setType(TYPE);
            userPaperRepository.save(usrPaper);
        }
        return usrPaper;
    }

    private void addAnswer(UsrPaper usrPaper, UsrPaper exist) {
        int TOTALSCORE = 0;
        if (CollectionUtil.isEmpty(usrPaper.getQuestions())) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        //将list转map 键为题ID值为题
        Map<Integer, Question> questionMap = usrPaper.getQuestions().stream().collect(Collectors.toMap(Question::getId, item -> item));
        for (Question question : exist.getQuestions()) {
            Question item = questionMap.get(question.getId());
            if (item == null) {
                continue;
            }
            question.setUserAnswer(item.getUserAnswer());
            if (question.getAnswer().equals(question.getUserAnswer())) {
                exist.setTotalScore(TOTALSCORE += 2);
            }
        }
    }

}

//    public void method(UsrPaper usrPaper,String a){
//    }
//
//    public void method(UsrPaper usrPaper){
//        method(usrPaper,"aaaa");
//    }
