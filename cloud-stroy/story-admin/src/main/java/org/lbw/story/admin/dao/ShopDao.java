package org.lbw.story.admin.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.admin.dto.ShopDto;
import org.lbw.story.admin.model.Shop;


import java.util.List;

@Mapper
public interface ShopDao {

    List<Shop> findShopList(@Param("shopId") Integer shopId);

    @Insert("insert into t_shop(shop_name,shop_addr,shop_open) values(#{shop_name},#{shop_addr},#{shop_open})")
    void insertShop(ShopDto shopDto);

    @Update("update t_shop set shop_name = #{shop_name},shop_addr=#{shop_addr},shop_open=#{shop_open} where shop_id = #{shop_id}")
    void updateShop(ShopDto shopDto);

    @Delete("delete from t_shop where shop_id = #{shop_id}")
    void deleteShop(Integer shop_id);
}
