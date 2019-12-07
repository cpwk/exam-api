package com.yang.exam.api.support.SupportService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import com.yang.exam.api.support.model.SupportError;
import com.yang.exam.api.support.model.VCode;
import com.yang.exam.commons.cache.CacheOptions;
import com.yang.exam.commons.cache.KvCacheFactory;
import com.yang.exam.commons.cache.KvCacheWrap;
import com.yang.exam.commons.exception.ServiceException;
import com.yang.exam.commons.mail.MailHelper;
import com.yang.exam.commons.mail.MailService;
import com.yang.exam.commons.sms.SmsConfig;
import com.yang.exam.commons.sms.SmsService;
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
public class SupportServiceImpl implements SupportService, SupportError {

    //验证码长度
    private static final int VCODE_LENGTH = 6;

    //取配置文件数据
    @Autowired
    private SmsConfig smsConfig;
    //
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

        vCodeCache = kvCacheFactory.create(new CacheOptions("m_code", 1, 300),
                new RepositoryProvider<Long, VCode>() {
                    @Override
                    public VCode findByKey(Long account) throws Exception {
                        throw new ServiceException(ERROR_MOBILE_VCODE_OVERTIME);
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
            throw new ServiceException(ERROR_VCODE_INVALID);
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
            mail.setSubject("重置密码");
            mail.setContent("您正在找回密码，验证码是：" + vCode.getCode() + " <br/>5分钟内有效");
            mailService.send(mail);
        }

    }

//    private class sendMobileVcodeTask extends ApiTask {
//        private String account;
//        private VCode vcode;
//
//        public sendMobileVcodeTask(String account, VCode vcode) {
//            this.account = account;
//            this.vcode = vcode;
//        }
//
//        @Override
//        protected void doApiWork() throws Exception {
//            JSONObject param = new JSONObject();
//            param.put("code", vcode.getCode());
//            if (sendSms(account, smsConfig.getVcodeTemplateCode(), param)) {
//                vCodeCache.save(vcode.getKey(), vcode);
//            }
//        }
//
//    }


//    private class sendEmailVCodeTask extends ApiTask {
//        private String account;
//        private VCode vCode;
//
//        public sendEmailVCodeTask(String account, VCode vCode) {
//            this.account = account;
//            this.vCode = vCode;
//        }
//
//        @Override
//        protected void doApiWork() {
//            MailHelper.MailInfo mail = new MailHelper.MailInfo();
//            mail.setToAddress(account);
//            mail.setSubject("重置密码");
//            mail.setContent("您正在找回密码，验证码是：" + vCode.getCode() + " <br/>5分钟内有效");
//            mailService.send(mail);
//            vCodeCache.save(vCode.getKey(), vCode);
//        }
//    }

//    private boolean sendSms(String account, String templateCode, JSONObject param) throws Exception {
//
//
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccesskeyId(),
//                smsConfig.getAccessKeySecret());
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain(smsConfig.getDomain());
//        request.setVersion(smsConfig.getVersion());
//        request.setAction("SendSms");
////        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", account);
//        request.putQueryParameter("SignName", "飞奔的跑跑");
//        request.putQueryParameter("TemplateCode", templateCode);
//        request.putQueryParameter("TemplateParam", JSON.toJSONString(param));
////        request.putQueryParameter("TemplateParam", "{\"code\":\"" + StringUtils.randomString(6) + "\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            JSONObject data = JSON.parseObject(response.getData());
//            if ("OK".equals(data.getString("Message"))) {
//                L.info("Send sms of vcode.mobile:" + account + ",vcode:" + param.getString("code"));
//                return true;
//            } else {
//                L.error(response.getData());
//                return false;
//            }
//        } catch (ServerException e) {
//            throw new ServiceException(ERR_DETAILED_MESSAGE, e.getMessage());
//        } catch (ClientException e) {
//            throw new ServiceException(ERROR_ALIYUN_EXCEPTION);
//        }
//    }

}