package com.yang.exam.api.banner.service;


import com.yang.exam.api.banner.model.Banner;
import com.yang.exam.api.banner.qo.BannerQo;

import java.util.List;

public interface IBannerService {

    List<Banner> banners(BannerQo qo, boolean admin);

    Banner banner(int id);

    void save(Banner banner) throws Exception;

    void remove(int id);
}
