package org.lbw.story.user.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.user.dto.CartDto;
import org.lbw.story.user.model.Cart;

import java.util.List;

@Mapper
public interface CartDao {

    @Select("select * from t_cart where u_id = #{u_id} and shop_id = #{shop_id} and meal_id = #{meal_id}")
    Cart findCart(CartDto cartDto);

    @Insert("insert into t_cart(u_id, shop_id, meal_id, cart_num) values(#{u_id}, #{shop_id}, #{meal_id}, 1)")
    void insertCart(CartDto cartDto);

    @Update("update t_cart set cart_num = cart_num+1 where u_id = #{u_id} and shop_id = #{shop_id} and  meal_id = #{meal_id}")
    void updateCartAddOne(CartDto cartDto);

    List<Cart> findCartList(CartDto cartDto);

    @Update("update t_cart set cart_num = #{cart_num} where cart_id = #{cart_id}")
    void updateCartNum(CartDto cartDto);

    void deleteCart(@Param("cart_ids") Integer[] cart_ids);

    @Delete("delete from t_cart where cart_id = #{cart_id}")
    void removeCartfood(CartDto cartDto);


    @Update("update t_shop_meal set sm_click = sm_click + 1 where shop_id = #{shop_id} AND meal_id = #{meal_id}")
    void updateShopMeal_Click(CartDto cartDto);
}
