package org.lbw.story.pub.dto;

import lombok.Data;

@Data
public class ShopMealDto {

    private Integer shop_id;
    private Integer meal_id;
    private Integer sm_store;
    private Boolean sm_recommend;
    private String opr;//查询tab操作
}
