package org.lbw.story.admin.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.admin.dto.ShopMealDto;
import org.lbw.story.admin.model.Meal;
import org.lbw.story.admin.model.Shop;
import org.lbw.story.admin.model.ShopMeal;
import org.lbw.story.common.TokenUser;

import java.util.List;

@Mapper
public interface ShopMealDao {
    List<Shop> findShopList(TokenUser tokenUser);

    List<Meal> findShopMealList(Integer shop_id);

    @Select("select meal_id,meal_name from t_meal")
    List<Meal> findMealList();

    List<ShopMeal> findShopMealListByShopIDs(@Param("shop_ids") String[] shop_ids);

    @Update("update t_shop_meal set sm_recommend = #{sm_recommend} where shop_id = #{shop_id} and meal_id = #{meal_id}")
    void updateRecommend(ShopMealDto shopMealDto);

    @Insert("insert into t_shop_meal(shop_id,meal_id,sm_store,sm_sale,sm_click,sm_recommend) values(#{shop_id},#{meal_id},0,0,0,0)")
    void insertShopMeal(ShopMealDto shopMealDto);

    @Delete("delete from t_shop_meal where shop_id = #{shop_id} and meal_id = #{meal_id}")
    void deleteShopMeal(ShopMealDto shopMealDto);

    @Update("update t_shop_meal set sm_store = #{sm_store} where shop_id = #{shop_id} and meal_id = #{meal_id}")
    void updateStore(ShopMealDto shopMealDto);

    List<ShopMeal> findShopMealWithCheckedList(TokenUser tokenUser);
}
