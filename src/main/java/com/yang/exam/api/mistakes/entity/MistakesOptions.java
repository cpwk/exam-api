package com.yang.exam.api.mistakes.entity;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/19 10:27
 * @Version：1.0
 */
public class MistakesOptions {

    private boolean withQuestion;

    public MistakesOptions() {
    }

    public static MistakesOptions getDefaultInstance() {
        return new MistakesOptions().setWithQuestion(true);
    }

    public boolean isWithQuestion() {
        return withQuestion;
    }

    public MistakesOptions setWithQuestion(boolean withQuestion) {
        this.withQuestion = withQuestion;
        return this;
    }
}
