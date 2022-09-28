package org.lbw.story.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lbw.story.user.model.Shop;

import java.util.List;

@Mapper //该注解表示这是一个访问数据库的对象（mybatis的映射器）
public interface ShopDao {

    @Select("select * from t_shop")
    public List<Shop> findShopList();
}
