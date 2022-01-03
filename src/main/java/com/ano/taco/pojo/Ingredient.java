package com.ano.taco.pojo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/28 11:21
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
    @Id
    @NaturalId(mutable = true)
    private final String id;
    private final String name;

    @Enumerated(EnumType.STRING)
    private final Type type;

    public enum Type {
        /**
         * 配料类型
         */
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }

}
