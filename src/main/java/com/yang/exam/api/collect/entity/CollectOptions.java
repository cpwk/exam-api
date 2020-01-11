package com.yang.exam.api.collect.entity;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/19 11:38
 * @Version：1.0
 */
public class CollectOptions {

    private boolean withQuestion;

    public CollectOptions() {
    }

    public static CollectOptions getDefaultInstance() {
        return new CollectOptions().setWithQuestion(true);
    }

    public boolean isWithQuestion() {
        return withQuestion;
    }

    public CollectOptions setWithQuestion(boolean withQuestion) {
        this.withQuestion = withQuestion;
        return this;
    }
}
