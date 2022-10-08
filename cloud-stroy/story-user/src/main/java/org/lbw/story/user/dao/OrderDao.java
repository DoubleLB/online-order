package org.lbw.story.user.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.user.dto.OrderDto;
import org.lbw.story.user.model.Order;

import java.util.List;

@Mapper
public interface OrderDao {
    @Insert("insert into t_order(u_id,shop_id,order_status) values(#{u_id},#{shop_id},0)")
    void insertOrder(@Param("u_id") String userId, @Param("shop_id") Integer shop_id);

    @Select("select LAST_INSERT_ID()")
    Integer findLastOrderId();


    void insertOrderDetails(@Param("order_id") Integer order_id,@Param("cart_ids") Integer[] cart_ids);

    @Update("update t_order " +
            "set order_submit = now(), " +
            "    order_status = 1, " +
            "    order_amount = (select sum(od.od_num*m.meal_price) " +
            "                    from t_order_details od join t_meal m on od.meal_id = m.meal_id " +
            "                    where od.order_id = #{order_id})  " +
            "where order_id = #{order_id}")
    void updateOrderAmount_submit(Integer order_id);

    List<Order> findOrderDetails(@Param("u_id") String userId);


    void updateShopMeal(@Param("shop_id")Integer shop_id,@Param("cart_ids") Integer[] cart_ids);

    @Update("UPDATE t_order SET order_status = 2 WHERE order_id = #{order_id}")
    void deleteOrder(OrderDto orderDto);

    @Update("UPDATE t_order SET order_status = order_status - 5 WHERE order_id = #{order_id}")
    void deleteHistoryOrder(OrderDto orderDto);
}
