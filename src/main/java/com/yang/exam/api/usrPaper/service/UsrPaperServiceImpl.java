package com.yang.exam.api.usrPaper.service;

import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
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
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.context.Contexts;
import com.yang.exam.commons.entity.Constants;
import com.yang.exam.commons.exception.RepositoryException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
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

    //
    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, UsrPaper> usrPaperCache;

    @PostConstruct
    public void init() {
        usrPaperCache = kvCacheFactory.create(new CacheOptions("UsrPaper", 1, Constants.CACHE_REDIS_EXPIRE),
                new RepositoryProvider<Integer, UsrPaper>() {

                    @Override
                    public UsrPaper findByKey(Integer key) throws RepositoryException {
                        return userPaperRepository.findById(key).orElse(null);
                    }

                    @Override
                    public Map<Integer, UsrPaper> findByKeys(Collection<Integer> ids) throws RepositoryException {
                        throw new UnsupportedOperationException("findByKeys");
                    }

                }, new BeanModelConverter<>(UsrPaper.class));
    }

//    @Override
//    public Map start(Integer id) throws Exception {
//        UsrPaper usrPaper = usrPaper(id,true);
//        Template template = templateService.getById(usrPaper.getTemplateId());
//        if (usrPaper.getType() == INVALID_TYPE) {
//            for (Question question : usrPaper.getQuestions()) {
//                question.setAnswer(null);
//            }
//        }
//        return CollectionUtil.arrayAsMap("usrPaper", usrPaper, "template", template);
//    }

    @Override
    public UsrPaper usrPaper(int id, boolean init) {
        UsrPaper usrPaper = usrPaper(id);
        if (init) {
        }
        return usrPaper;
    }

    private UsrPaper usrPaper(int id) {
        return usrPaperCache.findByKey(id);
    }

//


    @Override
    public void save(UsrPaper usrPaper) throws Exception {
        usrPaper.setUserId(Contexts.requestUser().getId());
        usrPaper.setTotalTime(TOTAL_TIME);
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
        userPaperRepository.save(exist);
    }

    @Override
    public Page<UsrPaper> record(UsrPaperQo usrPaperQo) throws Exception {
        return userPaperRepository.findAll(usrPaperQo);
    }

    @Override
    public List<Question> questions(UsrPaper usrPaper) throws Exception {
        List<Integer> questionsIds = new ArrayList<>();
        for (TemplateContent v : usrPaper.getContent()) {
            questionsIds.addAll(questionService.randomQuestionList(usrPaper.getCategoryId(), v.getType(), usrPaper.getDifficulty(), STATUS_OK, v.getNumber()));
        }
        return questionService.findListByIds(questionsIds);
    }

    @Override
    public UsrPaper saveMockExam(Integer templateId) throws Exception {
        return saveUsrPaper(templateId);
    }

    @Override
    public Map start(Integer id) throws Exception {
        UsrPaper usrPaper = getById(id);
        Template template = templateService.getById(usrPaper.getTemplateId());
        if (usrPaper.getType() == INVALID_TYPE) {
            for (Question question : usrPaper.getQuestions()) {
                question.setAnswer(null);
            }
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
        userPaperRepository.save(exist);
        saveMistakes(exist);
        return CollectionUtil.arrayAsMap("usrPaper", exist, "template", template);
    }

    @Override
    public Map usrPaperId(Integer id) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        UsrPaper usrPaper = getById(id);
        if (usrPaper.getType() == INVALID_TYPE) {
            for (Question question : usrPaper.getQuestions()) {
                question.setAnswer(null);
            }
        }
        List<Collect> collects = collectService.findByUserId(userId);
        return CollectionUtil.arrayAsMap("usrPaper", usrPaper, "collects", collects);

    }

    private String getTime() {
        return "练习试卷" + System.currentTimeMillis() % 100000;
    }

    private void saveMistakes(UsrPaper usrPaper) throws Exception {
        List<Integer> mistakesIds = new ArrayList<>();
        for (Question question : usrPaper.getQuestions()) {
            if (!question.getAnswer().equals(question.getUserAnswer())) {
                if (!mistakesIds.contains(question.getId())) {
                    mistakesIds.add(question.getId());
                }
            }
        }
        Mistakes mistakes = mistakesService.findByUserId(usrPaper.getUserId());
        if (mistakes == null) {
            Mistakes mistakes1 = new Mistakes();
            mistakes1.setUserId(usrPaper.getUserId());
            mistakes1.setQuestionId(mistakesIds);
            mistakesService.save(mistakes1);
        } else {
            mistakes.setQuestionId(mistakesIds);
            mistakesService.save(mistakes);
        }

    }

    private UsrPaper saveUsrPaper(Integer templateId) throws Exception {
        Integer userId = Contexts.requestUser().getId();
        long NAME = System.currentTimeMillis() % 100000;
        long k = System.currentTimeMillis();
        Template template = templateService.templateId(templateId);
        long i = System.currentTimeMillis();
        System.out.println(i - k);
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
            usrPaper.setType(INVALID_TYPE);
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
            if (exist.getTotalScore() == null) {
                exist.setTotalScore(TOTALSCORE);
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
