package org.lbw.story.admin.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.admin.dto.AdminDto;
import org.lbw.story.admin.model.Admin;
import org.lbw.story.admin.model.Shop;

import java.util.List;

@Mapper
public interface AdminDao {

    @Update("update t_admin set a_pwd = #{a_pwd} where a_id = #{a_name}")
    void updatePwd(@Param("a_name") String a_name,@Param("a_pwd") String a_pwd);

    List<Admin> findAdmin(@Param("shopId") Integer shopId);

    @Insert("insert into t_admin(a_id,shop_id,a_name,a_pwd,a_update) values(#{a_id},#{shop_id},#{a_name},#{a_pwd},now())")
    void insertAdmin(AdminDto adminDto);

    @Select("select shop_id,shop_name from t_shop")
    List<Shop> getShopList();

    @Update("update t_admin set a_name = #{a_name},shop_id = #{shop_id} where a_id = #{a_id}")
    void updateAdmin(AdminDto adminDto);

    @Delete("delete from t_admin where a_id = #{a_id}")
    void deleteAdmin(String a_id);

}
