package com.ano.taco.data;

import com.ano.taco.pojo.Taco;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/31 15:28
 */
public interface TacoRepository {
    /**
     * insert a Taco design Data
     * @param design
     * @return
     */
    Taco save(Taco design);
}
