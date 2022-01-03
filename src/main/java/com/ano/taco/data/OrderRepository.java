package com.ano.taco.data;

import com.ano.taco.pojo.Order;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/31 15:29
 */
public interface OrderRepository {
    /**
     * insert an Order data
     * @param order
     * @return
     */
    Order save(Order order);
}
