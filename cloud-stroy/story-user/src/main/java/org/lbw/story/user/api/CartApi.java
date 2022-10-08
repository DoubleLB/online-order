package org.lbw.story.user.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.lbw.story.user.dao.CartDao;
import org.lbw.story.user.dto.CartDto;
import org.lbw.story.user.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //该注解会导致编译时自动创建一个日志输出对象log
@Transactional
@RestController
@RequestMapping("/user/cart")//本API接口类统一的接口地址前缀
public class CartApi {

    @Autowired
    private CartDao cartDao;

    //加购物车
    @PostMapping("")
    public Result addCart(@RequestBody CartDto cartDto,
                          @RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token) {

        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        cartDto.setU_id(tokenUser.getUserId());

        //依照用户账号、门店id、餐品id查询购物车数据
        Cart cart = cartDao.findCart(cartDto);
        if (cart == null) {//没有符合条件的数据
            cartDao.insertCart(cartDto);
        }else{//有符合条件的数据（有指定的用户账号、门店id、餐品id的购物车数据）
            cartDao.updateCartAddOne(cartDto);//将查到的购物车中的数据中数量增1
        }

        cartDao.updateShopMeal_Click(cartDto);

        return  Result.OK();
    }

    //根据门店id获取购物车列表数据
    @GetMapping("")
    public Result cartList(CartDto cartDto,
                           @RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){
        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        cartDto.setU_id(tokenUser.getUserId());
        List<Cart> list = cartDao.findCartList(cartDto);

        return Result.OK(list);
    }

    //修改购物车数量
    @PutMapping("/num")
    public Result updateCartNum(@RequestBody CartDto cartDto){
        cartDao.updateCartNum(cartDto);

        return Result.OK();
    }

    //删除购物车商品
    @PostMapping("/remove")
    public Result removeCart(@RequestBody CartDto cartDto){
        cartDao.removeCartfood(cartDto);

        return Result.OK();
    }
}
