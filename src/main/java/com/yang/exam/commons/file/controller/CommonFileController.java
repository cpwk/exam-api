package com.yang.exam.commons.file.controller;

import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.file.service.ImgBase64Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/common/file")
public class CommonFileController extends BaseController {

    @RequestMapping(value = "/img_to_base64")
    @Action(session = SessionType.NONE)
    public ModelAndView img_to_base64(String url) throws Exception {
        return feedback(ImgBase64Utils.base64FromURL(url));
    }

}
