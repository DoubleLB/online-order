package org.lbw.story.user.api;


import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.lbw.story.user.dao.CartDao;
import org.lbw.story.user.dao.OrderDao;
import org.lbw.story.user.dto.OrderDto;
import org.lbw.story.user.dto.OrderTreeDto;
import org.lbw.story.user.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j //该注解会导致编译时自动创建一个日志输出对象log
@Transactional//事务管理
@RestController
@RequestMapping("/user/order")//本API接口类统一的接口地址前缀
public class OrderApi {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartDao cartDao;

    //添加购物订单
    @PostMapping("/add/{shop_id}")
    public Result addOrder(
                            @PathVariable Integer shop_id,
                            @RequestBody Integer[] cart_ids,
                            @RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token
    ){
        TokenUser tokenUser = TokenUtils.getTokenUser(token);

        //插入新订单
        orderDao.insertOrder(tokenUser.getUserId(),shop_id);

        //获取最新的订单id
        Integer order_id = orderDao.findLastOrderId();

        //插入订单明细
        orderDao.insertOrderDetails(order_id,cart_ids);

        //更新订单金额、提交时间和订单状态
        orderDao.updateOrderAmount_submit(order_id);

        //更新门店商品信息库存和销量内容
        orderDao.updateShopMeal(shop_id,cart_ids);

        //从购物车中删除已生成的订单信息
        cartDao.deleteCart(cart_ids);

        return Result.OK();

    }

    //获取订单信息
    @GetMapping("")
    public Result<List<OrderTreeDto>> allOrder(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){

        TokenUser tokenUser = TokenUtils.getTokenUser(token);

        List<Order> orderList =  orderDao.findOrderDetails(tokenUser.getUserId());

        //对数据进行扁平处理
        List<OrderTreeDto> mealList = new ArrayList<>();
        OrderTreeDto currentMeal = null;

        for(Order o : orderList){
            if(currentMeal == null || !currentMeal.getOrder_submit().equals(o.getOrder_submit())){
                //当前没有树结构餐品订单 或者 当前遍历的数据库查询数据的订单提交时间和当前树结构订单信息不匹配
                currentMeal = new OrderTreeDto();
                currentMeal.setOrder_id(o.getOrder_id());
                currentMeal.setShop_name(o.getShop_name());
                currentMeal.setOrder_amount(o.getOrder_amount());
                currentMeal.setOrder_status(o.getOrder_status());
                currentMeal.setOrder_submit(o.getOrder_submit());
                currentMeal.setMealAndNumList(new ArrayList<OrderTreeDto.MealAndNum>());
                mealList.add(currentMeal);
            }

            OrderTreeDto.MealAndNum mealAndNum = new OrderTreeDto.MealAndNum();
            mealAndNum.setMeal_name(o.getMeal_name());
            mealAndNum.setMeal_pic(o.getMeal_pic());
            mealAndNum.setMeal_price(o.getMeal_price());
            mealAndNum.setOd_num(o.getOd_num());

            currentMeal.getMealAndNumList().add(mealAndNum);
        }

        return Result.OK(mealList);
    }

    //删除订单
    @PostMapping("/delete")
    public Result deleteorder(@RequestBody OrderDto orderDto){

        orderDao.deleteOrder(orderDto);

        return Result.OK();
    }

    //删除历史订单
    @PostMapping("/delete-history")
    public Result deletehistoryorder(@RequestBody OrderDto orderDto){

        orderDao.deleteHistoryOrder(orderDto);

        return Result.OK();
    }

}
