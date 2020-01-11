package com.yang.exam.commons.support.SupportService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.support.config.mail.MailHelper;
import com.yang.exam.commons.support.config.mail.MailService;
import com.yang.exam.commons.support.config.sms.SmsService;
import com.yang.exam.commons.support.config.sms.SmsTpl;
import com.yang.exam.commons.support.entity.SupportError;
import com.yang.exam.commons.support.model.VCode;
import com.yang.exam.commons.task.ApiTask;
import com.yang.exam.commons.task.TaskService;
import com.yang.exam.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

@Service
public class SupportServiceImpl implements SupportService, SupportError {

    //验证码长度
    private static final int VCODE_LENGTH = 6;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MailService mailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Long, VCode> vCodeCache;

    @PostConstruct
    public void init() {

        vCodeCache = kvCacheFactory.create(new CacheOptions("code", 1, 300),
                new RepositoryProvider<Long, VCode>() {
                    @Override
                    public VCode findByKey(Long account) throws Exception {
                        throw new ServiceException(ERROR_VCODE);
                    }

                    @Override
                    public Map<Long, VCode> findByKeys(Collection<Long> collection) throws Exception {
                        return null;
                    }
                }, new BeanModelConverter<>(VCode.class));

    }

    @Override
    public VCode getVcode(Long Key) throws Exception {
        try {
            return vCodeCache.findByKey(Key);
        } catch (Exception e) {
            throw new ServiceException(ERROR_VCODE);
        }
    }

    @Override
    public void saveVCode(Long key, VCode vCode) {
        vCodeCache.save(key, vCode);
    }

    @Override
    public void sendSms(VCode vCode) throws Exception {
//        boolean isMobile = vCode.getAccountType() == VCodeConstants.MOBILE;
        vCode.setCode(StringUtils.getRandNum(VCODE_LENGTH));
        saveVCode(vCode.getKey(), vCode);
        if (StringUtils.isChinaMobile(vCode.getAccount())) {
            taskService.addTask(new SendVCodeSmsTask(vCode.getAccount(), vCode.getCode()));
        } else if (StringUtils.isEmail(vCode.getAccount())) {
            taskService.addTask(new sendEmailVCodeTask(vCode));
        } else {
            throw new ServiceException(ERROR_SEND_FAIL);
        }
    }

    private class SendVCodeSmsTask extends ApiTask {
        private String mobile;
        private String code;

        public SendVCodeSmsTask(String mobile, String code) {
            super();
            this.mobile = mobile;
            this.code = code;
        }

        @Override
        protected void doApiWork() {

            SmsTpl smsTpl = new SmsTpl();
            smsTpl.setPhoneNumbers(mobile);
            smsTpl.setTemplateCode(SmsTpl.tpl_mod_pwd);
            JSONObject object = new JSONObject();
            object.put("code", code);
            smsTpl.setTemplateParam(JSON.toJSONString(object));

            smsService.send(smsTpl);
        }
    }

    private class sendEmailVCodeTask extends ApiTask {

        private VCode vCode;

        public sendEmailVCodeTask(VCode vCode) {
            super();
            this.vCode = vCode;
        }

        @Override
        protected void doApiWork() {
            MailHelper.MailInfo mail = new MailHelper.MailInfo();
            mail.setToAddress(vCode.getAccount());
            mail.setSubject("邮箱验证码");
            mail.setContent("您本次操作的验证码为：" + vCode.getCode() + ",有效时间5分钟");
            mailService.send(mail);
        }

    }

}