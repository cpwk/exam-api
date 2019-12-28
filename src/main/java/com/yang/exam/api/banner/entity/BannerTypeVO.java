package com.yang.exam.api.banner.entity;


import com.yang.exam.commons.entity.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class BannerTypeVO {

    private static List<KeyValue> steps = null;

    public static List<KeyValue> getTypes() {
        if (steps == null) {
            steps = new ArrayList<>();
            for (BannerType type : BannerType.values()) {
                steps.add(new KeyValue(type.getKey(), type.getVal()));
            }
        }
        return steps;
    }

}
