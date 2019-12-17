package com.yang.exam.api.question.entity;

import com.yang.exam.api.question.model.Question;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/12 16:05
 * @Version：1.0
 */
public class QuestionOptions {

    private boolean withCategory;

    private boolean withTag;

    private QuestionOptions(){

    }

    public boolean isWithCategory() {
        return withCategory;
    }

    public boolean isWithTag() {
        return withTag;
    }

    private QuestionOptions setWithCategory(boolean withCategory) {
        this.withCategory = withCategory;
        return this;
    }

    private QuestionOptions setWithTag(boolean withTag) {
        this.withTag = withTag;
        return this;
    }

    public static QuestionOptions getDefaultInstance(){
        return new QuestionOptions().setWithCategory(true).setWithTag(false);
    }

    public static QuestionOptions getOmsListInstance() {
        return new QuestionOptions().setWithCategory(true).setWithTag(true);
    }

    public static QuestionOptions getUsrListInstance(){
        return new QuestionOptions().setWithCategory(true).setWithTag(true);
    }

}
