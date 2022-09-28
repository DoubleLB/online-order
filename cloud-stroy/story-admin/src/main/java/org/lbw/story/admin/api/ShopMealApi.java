package org.lbw.story.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.admin.dao.ShopMealDao;
import org.lbw.story.admin.dto.ShopMealDto;
import org.lbw.story.admin.dto.ShopTreeDto;
import org.lbw.story.admin.model.Meal;
import org.lbw.story.admin.model.Shop;
import org.lbw.story.admin.model.ShopMeal;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/admin/shopmeal")
public class ShopMealApi {

    @Autowired
    private ShopMealDao shopMealDao;

    @GetMapping("/shop")
    public Result<List<Shop>> shopList(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){
        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        List<Shop> shopList = shopMealDao.findShopList(tokenUser);
        return Result.OK(shopList);

    }

    @GetMapping("/full")
    public Result<List<ShopTreeDto>> shopListFull(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){
        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        List<ShopMeal> shopMealList = shopMealDao.findShopMealWithCheckedList(tokenUser);

        //将从数据库查询出的扁平化数据转化为树状层次数据findShopMealWithCheckedList(tokenUser)
        List<ShopTreeDto> shopList = new ArrayList<>();
        ShopTreeDto currentShop = null;

        for (ShopMeal sm : shopMealList){
            if(currentShop == null || !currentShop.getShop_id().equals(sm.getShop_id())){
                //当前没有树结构门店 或者 当前遍历的数据库查询数据的门店编号和当前树结构门店不匹配
                currentShop = new ShopTreeDto();
                currentShop.setShop_id(sm.getShop_id());
                currentShop.setShop_name(sm.getShop_name());
                currentShop.setMealList(new ArrayList<ShopTreeDto.Meal>());
                shopList.add(currentShop);
            }

            ShopTreeDto.Meal meal = new ShopTreeDto.Meal();
            meal.setMeal_id(sm.getMeal_id());
            meal.setMeal_name(sm.getMeal_name());
            meal.setChecked(sm.getChecked());
            meal.setSm_store(sm.getSm_store());
            meal.setSm_recommend(sm.getSm_recommend());

            currentShop.getMealList().add(meal);
        }

        return Result.OK(shopList);

    }

    @GetMapping("/meal-all")
    public Result<List<Meal>> mealList(){
        List<Meal> mealList = shopMealDao.findMealList();
        return Result.OK(mealList);
    }

    //根据前端传来的若干shopId（多个shopId逗号隔开）
    @GetMapping("")
    public Result<List<ShopMeal>> shopMealList(String shopIds){
        String[] shop_ids = shopIds.split(",");
        List<ShopMeal> list = shopMealDao.findShopMealListByShopIDs(shop_ids);
        return Result.OK(list);
    }

    //店长推荐或者取消
    @PutMapping("/recommend")
    public Result recommend(@RequestBody ShopMealDto shopMealDto){
        shopMealDao.updateRecommend(shopMealDto);
        return Result.OK();
    }

    //门店上架餐品和下架餐品
    @PutMapping("")
    public Result addAndDelShopMeal(@RequestBody ShopMealDto shopMealDto){
        if("add".equals(shopMealDto.getOpr())){
            shopMealDao.insertShopMeal(shopMealDto);
        }else {
            shopMealDao.deleteShopMeal(shopMealDto);
        }
        return Result.OK();
    }

    //更新库存
    @PutMapping("/store")
    public Result updateShopMealStore(@RequestBody ShopMealDto shopMealDto){
        shopMealDao.updateStore(shopMealDto);
        return Result.OK();
    }

    @GetMapping("/meal/{shop_id}")
    public Result<List<Meal>> shopMealList(@PathVariable Integer shop_id){
        List<Meal> list = shopMealDao.findShopMealList(shop_id);
        return Result.OK(list);
    }
}
