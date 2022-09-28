package org.lbw.story.pub.model;

import lombok.Data;

@Data
public class ShopMeal {

    private Integer sm_id;
    private Integer shop_id;
    private Integer meal_id;

    private Integer sm_store;
    private Integer sm_sale;
    private Integer sm_click;
    private Boolean sm_recommend;

    private String shop_name;
    private String meal_name;
    private Double meal_price;
    private String meal_pic;
    private String meal_desc;

    private Integer cls_id;
    private String cls_name;

}
