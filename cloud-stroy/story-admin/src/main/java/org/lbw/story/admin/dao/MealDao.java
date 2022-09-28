package org.lbw.story.admin.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.admin.dto.MealDto;
import org.lbw.story.admin.model.Meal;

import java.util.List;

@Mapper
public interface MealDao {
    @Select("select m.*,c.cls_name from t_meal m join t_cls c on m.cls_id = c.cls_id")
    List<Meal> findMealList();

    @Insert("insert into t_meal(meal_name,cls_id,meal_price,meal_desc,insert_time,meal_pic) values(#{meal_name},#{cls_id},#{meal_price},#{meal_desc},#{insert_time},#{meal_pic})")
    void insertMeal(MealDto mealDto);

    @Update("update t_meal set meal_name=#{meal_name} , cls_id = #{cls_id} , meal_price=#{meal_price},meal_desc=#{meal_desc} where meal_id = #{meal_id}")
    void UpdateMeal(MealDto mealDto);

    @Delete("delete from t_meal where meal_id = #{meal_id}")
    void deleteMealById(Integer meal_id);
}
