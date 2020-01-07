package com.yang.exam.commons.common.service;

import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.RepositoryException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.support.model.VCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

@Service
public class CommonService implements ICommonService, ErrorCode {

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Long, VCode> VCodeCache;

    @PostConstruct
    public void init() {
        VCodeCache = kvCacheFactory.create(new CacheOptions("img_code", 2, 600),
                new RepositoryProvider<Long, VCode>() {

                    @Override
                    public VCode findByKey(Long key) {
                        throw new RuntimeException();
                    }

                    @Override
                    public Map<Long, VCode> findByKeys(Collection<Long> ids) throws RepositoryException {
                        throw new UnsupportedOperationException("findByKeys");
                    }

                }, new BeanModelConverter<>(VCode.class));
    }

    @Override
    public void saveVCode(Long key, VCode vCode) {
        VCodeCache.save(key, vCode);
    }

    @Override
    public VCode getValCode(Long key) throws Exception {
        try {
            return VCodeCache.findByKey(key);
        } catch (Exception e) {
            throw new ServiceException(ERROR_VALCODE);
        }
    }


}
