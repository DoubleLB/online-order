package org.lbw.story.user.api;


import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.lbw.story.user.dao.CartDao;
import org.lbw.story.user.dao.OrderDao;
import org.lbw.story.user.dto.OrderDto;
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

        //更新订单金额、提交时间和状态
        orderDao.updateOrderAmount_submit(order_id);

        //从购物车中删除已生成的订单信息
        cartDao.deleteCart(cart_ids);

        return Result.OK();

    }

    //订单明细
    @GetMapping("")
    public Result<List<OrderDto>> allOrder(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){

        TokenUser tokenUser = TokenUtils.getTokenUser(token);

        List<Order> orderList =  orderDao.findOrderDetails(tokenUser.getUserId());

        //对数据进行扁平处理
        List<OrderDto> mealList = new ArrayList<>();
        OrderDto currentMeal = null;

        for(Order o : orderList){
            if(currentMeal == null || !currentMeal.getOrder_submit().equals(o.getOrder_submit())){
                //当前没有树结构餐品订单 或者 当前遍历的数据库查询数据的订单提交时间和当前树结构订单信息不匹配
                currentMeal = new OrderDto();
                currentMeal.setShop_name(o.getShop_name());
                currentMeal.setOrder_amount(o.getOrder_amount());
                currentMeal.setOrder_status(o.getOrder_status());
                currentMeal.setOrder_submit(o.getOrder_submit());
                currentMeal.setMealAndNumList(new ArrayList<OrderDto.MealAndNum>());
                mealList.add(currentMeal);
            }

            OrderDto.MealAndNum mealAndNum = new OrderDto.MealAndNum();
            mealAndNum.setMeal_name(o.getMeal_name());
            mealAndNum.setMeal_pic(o.getMeal_pic());
            mealAndNum.setMeal_price(o.getMeal_price());
            mealAndNum.setOd_num(o.getOd_num());

            currentMeal.getMealAndNumList().add(mealAndNum);
        }

        return Result.OK(mealList);
    }

}
