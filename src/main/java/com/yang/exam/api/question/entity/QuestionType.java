package com.yang.exam.api.question.entity;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/4 16:14
 * @Version：1.0
 */
public enum QuestionType {

    SELECT((byte) 1), MSELECT((byte) 2), COMPLETION((byte) 3), SAQS((byte) 4);

    private Byte value;

    QuestionType(byte b) {
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }
}
