package com.yang.exam.api.banner.service;


import com.yang.exam.api.banner.model.Banner;
import com.yang.exam.api.banner.qo.BannerQo;
import com.yang.exam.commons.exception.ServiceException;

import java.util.List;

public interface IBannerService {

    List<Banner> banners(BannerQo qo, boolean admin);

    Banner banner(int id);

    void save(Banner banner) throws ServiceException;

    void remove(int id);
}
