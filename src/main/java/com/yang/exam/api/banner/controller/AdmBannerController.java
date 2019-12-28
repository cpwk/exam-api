package com.yang.exam.api.banner.controller;

import com.yang.exam.api.banner.entity.BannerTypeVO;
import com.yang.exam.api.banner.model.Banner;
import com.yang.exam.api.banner.qo.BannerQo;
import com.yang.exam.api.banner.service.IBannerService;
import com.yang.exam.commons.controller.Action;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.controller.SessionType;
import com.yang.exam.commons.entity.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/adm/banner")
public class AdmBannerController extends BaseController {

    @Autowired
    private IBannerService bannerService;

    private List<KeyValue> bannerTypes = BannerTypeVO.getTypes();

    @RequestMapping(value = "/bannerTypes")
    @Action(session = SessionType.ADMIN)
    public ModelAndView bannerTypes() throws Exception {
        return feedback(bannerTypes);
    }

    @RequestMapping(value = "/save")
    @Action(session = SessionType.ADMIN)
    public ModelAndView save(String banner) throws Exception {
        bannerService.save(parseModel(banner, new Banner()));
        return feedback(null);
    }

    @RequestMapping(value = "/remove")
    @Action(session = SessionType.ADMIN)
    public ModelAndView remove(Integer id) throws Exception {
        bannerService.remove(id);
        return feedback(null);
    }

    @RequestMapping(value = "/banner")
    @Action(session = SessionType.ADMIN)
    public ModelAndView banner(Integer id) throws Exception {
        return feedback(bannerService.banner(id));
    }

    @RequestMapping(value = "/banners")
    @Action(session = SessionType.ADMIN)
    public ModelAndView banners(String bannerQo) throws Exception {
        return feedback(bannerService.banners(parseModel(bannerQo, new BannerQo()), true));
    }

}