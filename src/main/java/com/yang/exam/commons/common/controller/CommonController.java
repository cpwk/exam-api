package com.yang.exam.commons.common.controller;

import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.common.entity.FormItemTypeVO;
import com.yang.exam.commons.common.entity.SimpleArticle;
import com.yang.exam.commons.common.service.ICommonService;
import com.yang.exam.commons.common.service.WXArticleService;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.entity.KeyValue;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.support.model.VCode;
import com.yang.exam.commons.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController implements ErrorCode {

    @Autowired
    private ICommonService commonService;

    private List<KeyValue> formTypes = FormItemTypeVO.getTypes();

    @RequestMapping(value = "/gen_valCode_signin")
    @Action(session = SessionType.NONE)
    public void genValCode(HttpServletRequest request, HttpServletResponse response, Long key) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            String randomString = randomValidateCode.getRandcode(request, response, key);//输出验证码图片方法
            commonService.saveVCode(key, new VCode(randomString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/formTypes")
    @Action(session = SessionType.NONE)
    public ModelAndView formTypes() throws Exception {
        return feedback(formTypes);
    }

    @RequestMapping(value = "/fetch_article")
    @Action(session = SessionType.NONE)
    public ModelAndView fetch_article(String url) throws Exception {
        SimpleArticle article;
        try {
            article = WXArticleService.fetch(url);
        } catch (IOException e) {
            throw new ServiceException(CRAWL_FAILED);
        }
        if (article == null || article.getContent() == null) {
            throw new ServiceException(ONLY_SUPPORTS_WX_ARTICLES);
        }
        return feedback(article);
    }

}
