package org.lbw.story.user.model;

import lombok.Data;

@Data
public class Cart {

    private Integer cart_id;
    private String u_id;
    private Integer shop_id;
    private Integer meal_id;
    private String meal_name;
    private Double meal_price;
    private String meal_pic;
    private Integer cart_num;
}
