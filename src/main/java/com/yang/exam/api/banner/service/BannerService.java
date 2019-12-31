package com.yang.exam.api.banner.service;

import com.yang.exam.api.banner.model.Banner;
import com.yang.exam.api.banner.qo.BannerQo;
import com.yang.exam.api.banner.repository.IBannerRepository;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService implements IBannerService, ErrorCode {

    @Autowired
    private IBannerRepository bannerRepository;

    @Override
    public List<Banner> banners(BannerQo qo, boolean admin) {
        if (admin) {
            qo.setStatus(0);
        }
        return bannerRepository.findAll(qo);
    }

    @Override
    public Banner banner(int id) {
        return bannerRepository.getOne(id);
    }

    @Override
    public void save(Banner banner) throws Exception {
        if (StringUtils.isEmpty(banner.getImg())) {
            throw new ServiceException(PLEASE_UPLOAD_IMAGE);
        }
        bannerRepository.save(banner);
    }

    @Override
    public void remove(int id) {
        bannerRepository.deleteById(id);
    }

}
