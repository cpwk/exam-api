package com.yang.exam.api.template.qo;

import com.yang.exam.commons.reposiotry.support.DataQueryObjectPage;
import com.yang.exam.commons.reposiotry.support.QueryField;
import com.yang.exam.commons.reposiotry.support.QueryType;

/**
 * @author: yangchengcheng
 * @Date: 2019/11/30 10:59
 * @Version：1.0
 */
public class TemplateQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @QueryField(type = QueryType.FULL_LIKE, name = "templateName")
    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}