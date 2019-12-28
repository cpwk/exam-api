package com.yang.exam.commons.common.service;

import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.entity.ErrorCode01;
import com.yang.exam.commons.entity.ValCode;
import com.yang.exam.commons.exception.RepositoryException;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.mail.MailService;
import com.yang.exam.commons.sms.ISmsService;
import com.yang.exam.commons.sms.SmsTpl;
import com.yang.exam.commons.task.ApiTask;
import com.yang.exam.commons.task.TaskService;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

@Service
public class CommonService implements ICommonService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Long, ValCode> valCodeCache;

    @PostConstruct
    public void init() {
        valCodeCache = kvCacheFactory.create(new CacheOptions("val_code", 2, 600),
                new RepositoryProvider<Long, ValCode>() {

                    @Override
                    public ValCode findByKey(Long key) {
                        throw new RuntimeException();
                    }

                    @Override
                    public Map<Long, ValCode> findByKeys(Collection<Long> ids) throws RepositoryException {
                        throw new UnsupportedOperationException("findByKeys");
                    }

                }, new BeanModelConverter<>(ValCode.class));
    }

    @Override
    public void saveValCode(Long key, ValCode valCode) {
        valCodeCache.save(key, valCode);
    }

    @Override
    public ValCode getValCode(Long key) throws ServiceException {
        try {
            return valCodeCache.findByKey(key);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode01.ERROR_VALCODE.getCode());
        }
    }


}
