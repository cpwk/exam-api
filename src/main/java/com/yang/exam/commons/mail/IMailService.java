package com.yang.exam.commons.mail;

import com.yang.exam.commons.mail.MailHelper.MailInfo;

public interface IMailService {

    boolean send(MailInfo mail);
}