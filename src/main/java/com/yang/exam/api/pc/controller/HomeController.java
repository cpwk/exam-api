package com.yang.exam.api.pc.controller;

import com.yang.exam.api.banner.qo.BannerQo;
import com.yang.exam.api.banner.service.IBannerService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pc/home")
public class HomeController extends BaseController {

    @Autowired
    private IBannerService bannerService;


    @RequestMapping(value = "/banners")
    @Action(session = SessionType.NONE)
    public ModelAndView banners(String bannerQo) throws Exception {
        return feedback(bannerService.banners(parseModel(bannerQo, new BannerQo()), false));
    }

}
