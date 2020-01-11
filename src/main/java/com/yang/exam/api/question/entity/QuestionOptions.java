package com.yang.exam.api.question.entity;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/12 16:05
 * @Version：1.0
 */
public class QuestionOptions {

    private boolean withCategory;

    private boolean withTag;

    private boolean withCollect;

    private QuestionOptions() {

    }

    public static QuestionOptions getDefaultInstance() {
        return new QuestionOptions().setWithCategory(true).setWithTag(false);
    }

    public static QuestionOptions getOmsListInstance() {
        return new QuestionOptions().setWithCategory(true).setWithTag(true);
    }

    public static QuestionOptions getUsrListInstance() {
        return new QuestionOptions().setWithCategory(false).setWithTag(false).setWithCollect(true);
    }

    public boolean isWithCategory() {
        return withCategory;
    }

    private QuestionOptions setWithCategory(boolean withCategory) {
        this.withCategory = withCategory;
        return this;
    }

    public boolean isWithTag() {
        return withTag;
    }

    private QuestionOptions setWithTag(boolean withTag) {
        this.withTag = withTag;
        return this;
    }

    public boolean isWithCollect() {
        return withCollect;
    }

    public QuestionOptions setWithCollect(boolean withCollect) {
        this.withCollect = withCollect;
        return this;
    }

}
