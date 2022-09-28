package org.lbw.story.user.dto;


import lombok.Data;

@Data
public class CartDto {

    private Integer cart_id;
    private String u_id;
    private Integer shop_id;
    private Integer meal_id;
    private Integer cart_num;
}
