package com.yang.exam.api.collect.controller;

import com.yang.exam.api.collect.entity.CollectOptions;
import com.yang.exam.api.collect.model.Collect;
import com.yang.exam.api.collect.qo.CollectQo;
import com.yang.exam.api.collect.service.CollectService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/18 18:59
 * @Version：1.0
 */
@RequestMapping(path = "/usr/collect")
@Controller
public class UsrCollectController extends BaseController {

    @Autowired
    private CollectService collectService;

    @RequestMapping(value = "save")
    @Action(session = SessionType.USER)
    public ModelAndView save(String collect) throws Exception {
        collectService.save(parseModel(collect, new Collect()));
        return feedback();
    }

    @RequestMapping(value = "delete")
    @Action(session = SessionType.USER)
    public ModelAndView delete(Integer id) throws Exception {
        collectService.delete(id);
        return feedback();
    }

    @RequestMapping(value = "collect_list")
    @Action(session = SessionType.USER)
    public ModelAndView collectList(String collectQo) throws Exception {
        return feedback(collectService.collectList(parseModel(collectQo, new CollectQo()), CollectOptions.getDefaultInstance()));
    }
}
