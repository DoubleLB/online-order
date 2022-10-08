package org.lbw.story.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.admin.dao.AdminOrderDao;
import org.lbw.story.admin.dto.AdminOrderTreeDto;
import org.lbw.story.admin.model.AdminOrder;
import org.lbw.story.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/admin/order")
public class AdminOrderApi {

    @Autowired
    private AdminOrderDao adminOrderDao;

    @GetMapping("/{shop_id}")
    public Result<List<AdminOrderTreeDto>> getOrder(@PathVariable Integer shop_id){

        List<AdminOrder> list = adminOrderDao.findShopOrder(shop_id);

        List<AdminOrderTreeDto> orderList = new ArrayList<>();
        AdminOrderTreeDto currentOrder = null;

        for (AdminOrder ao : list){
            if(currentOrder == null || !currentOrder.getOrder_id().equals(ao.getOrder_id())){
                currentOrder = new AdminOrderTreeDto();
                currentOrder.setOrder_id(ao.getOrder_id());
                currentOrder.setU_nickname(ao.getU_nickname());
                currentOrder.setOrder_status(ao.getOrder_status());
                currentOrder.setOrder_submit(ao.getOrder_submit());
                currentOrder.setOrder_amount(ao.getOrder_amount());
                currentOrder.setMealAndNumList(new ArrayList<AdminOrderTreeDto.MealAndNum>());
                orderList.add(currentOrder);
            }

            AdminOrderTreeDto.MealAndNum mealAndNum = new AdminOrderTreeDto.MealAndNum();
            mealAndNum.setOd_num(ao.getOd_num());
            mealAndNum.setMeal_name(ao.getMeal_name());
            mealAndNum.setMeal_pic(ao.getMeal_pic());
            mealAndNum.setMeal_price(ao.getMeal_price());

            currentOrder.getMealAndNumList().add(mealAndNum);
        }


        return Result.OK(orderList);
    }

    @PostMapping("/change")
    public Result DealOrder(@RequestBody AdminOrder adminOrder){

        adminOrderDao.updateAdminOrder(adminOrder);

        return Result.OK();
    }

}
