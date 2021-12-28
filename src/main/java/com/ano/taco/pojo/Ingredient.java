package com.ano.taco.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/28 11:21
 */
@Data
@RequiredArgsConstructor
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }

}
