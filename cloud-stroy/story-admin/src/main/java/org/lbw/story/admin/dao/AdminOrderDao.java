package org.lbw.story.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.lbw.story.admin.model.AdminOrder;

import java.util.List;

@Mapper
public interface AdminOrderDao {

    List<AdminOrder> findShopOrder(Integer shop_id);

    @Update("update t_order set order_status = 3 where order_id = #{order_id}")
    void updateAdminOrder(AdminOrder adminOrder);
}
