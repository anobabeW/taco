package com.ano.taco.data;

import com.ano.taco.pojo.Ingredient;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/30 15:58
 */
public interface IngredientRepository {
    /**
     * 查询所有配料信息
     * @return
     */
    Iterable<Ingredient> findAll();

    /**
     * 根据id,查询单个配料信息
     * @param id
     * @return
     */
    Ingredient findOne(String id);

    /**
     * 更新/插入数据，保存配料对象Ingredient
     * @param ingredient
     * @return
     */
    Ingredient save(Ingredient ingredient);
}
