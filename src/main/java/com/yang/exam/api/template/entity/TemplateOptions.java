package com.yang.exam.api.template.entity;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/21 13:46
 * @Version：1.0
 */
public class TemplateOptions {
    private boolean withQuestions;

    public TemplateOptions() {
    }

    public static TemplateOptions getDefaultInstance() {
        return new TemplateOptions().setWithQuestions(true);
    }

    public static TemplateOptions getOmsDefaultInstance() {
        return new TemplateOptions().setWithQuestions(false);
    }

    public boolean isWithQuestions() {
        return withQuestions;
    }

    public TemplateOptions setWithQuestions(boolean withQuestions) {
        this.withQuestions = withQuestions;
        return this;
    }
}
