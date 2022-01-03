package com.ano.taco.data;

import com.alibaba.fastjson.JSONObject;
import com.ano.taco.pojo.Order;
import com.ano.taco.pojo.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/31 15:31
 */
@Slf4j
@Repository
public class JdbcOrderRepository implements OrderRepository{
    private JdbcTemplate jdbc;

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
            this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 使用SimpleJdbcInsert插入数据
     * @param order
     * @return
     */
    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(orderId, taco);
        }
        return order;
    }

    private long saveOrderDetails(Order order) {
        log.info("orderInfo:{}",order.toString());
        @SuppressWarnings("unchecked")
        Map<String, Object> orderValues = objectMapper.convertValue(order, Map.class);
        //使用fastjson
//        Map<String, Object> orderValues = JSONObject.parseObject(JSONObject.toJSONString(order), HashMap.class);
        orderValues.put("placedAt", order.getPlacedAt());
        long orderId = orderInserter
                .executeAndReturnKey(orderValues)
                .longValue();
        return orderId;
    }

    private void saveTacoToOrder(long orderId, Taco taco) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
