package org.lbw.story.pub.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lbw.story.pub.dto.AdminLoginDto;
import org.lbw.story.pub.dto.LoginDto;
import org.lbw.story.pub.dto.RegisterDto;
import org.lbw.story.pub.dto.ShopMealDto;
import org.lbw.story.pub.model.*;

import java.util.List;

@Mapper
public interface PublicDao {
    @Select("select * from t_cls")
    List<Cls> findClsList();

    @Select("select a.*,s.shop_name from t_admin a left join t_shop s on a.shop_id = s.shop_id where a.a_id = #{a_id} and a.a_pwd = #{a_pwd}")
    Admin findAdminByIdAndPwd(AdminLoginDto adminLoginDto);

    @Select("select shop_id,shop_name from t_shop")
    List<Shop> findAllShopList();

    List<ShopMeal> findShopMealWithFull(ShopMealDto shopMealDto);

    @Select("select * from t_user where u_id = #{u_id}")
    User findUserById(String u_id);

    @Insert("insert into t_user(u_id,u_pwd,u_register) values(#{u_id},#{u_pwd},now())")
    void insertUser(RegisterDto dto);

    @Select("select * from t_user where u_id = #{u_id} and u_pwd = #{u_pwd}")
    User findUserByIdAndPwd(LoginDto loginDto);
}
