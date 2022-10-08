package org.lbw.story.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.lbw.story.admin.dao.ShopDao;
import org.lbw.story.admin.dto.ShopDto;
import org.lbw.story.admin.model.Shop;
import org.lbw.story.common.Result;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/admin/shop")
public class ShopApi {

    @Autowired
    private ShopDao shopDao;

    @GetMapping("")
    public Result<List<Shop>> shopList(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String Token){

        TokenUser tokenUser = TokenUtils.getTokenUser(Token);
        Integer shopId = tokenUser.getShopId();
        return Result.OK(shopDao.findShopList(shopId));
    }

    @PostMapping("")
    public Result shopDoAdd(@RequestBody ShopDto shopDto){
        shopDao.insertShop(shopDto);
        return Result.OK();
    }

    @PutMapping("")
    public Result shopDoUpd(@RequestBody ShopDto shopDto){
        shopDao.updateShop(shopDto);
        return Result.OK();
    }

    @DeleteMapping("/{shop_id}")
    public Result shopDoDel(@PathVariable Integer shop_id){
        shopDao.deleteShop(shop_id);
        return Result.OK();
    }

}
