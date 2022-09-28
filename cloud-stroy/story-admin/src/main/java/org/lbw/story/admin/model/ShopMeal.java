package org.lbw.story.admin.model;

import lombok.Data;

@Data
public class ShopMeal {
    private Integer shop_id;
    private Integer meal_id;
    private Integer sm_store;
    private Boolean sm_recommend;

    private String shop_name;
    private String meal_name;
    private Boolean checked;
}
