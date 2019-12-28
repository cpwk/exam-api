package com.yang.exam.commons.common.entity;


import com.yang.exam.commons.entity.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class FormItemTypeVO {

    private static List<KeyValue> steps = null;

    public static List<KeyValue> getTypes() {
        if (steps == null) {
            steps = new ArrayList<>();
            for (FormItemType type : FormItemType.values()) {
                steps.add(new KeyValue(type.getKey(), type.getLabel()));
            }
        }
        return steps;
    }

}
