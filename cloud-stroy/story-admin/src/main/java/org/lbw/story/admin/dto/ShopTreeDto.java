package org.lbw.story.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShopTreeDto {

    @Data
    public static class Meal{

        private Integer meal_id;
        private Integer sm_store;
        private Boolean sm_recommend;
        private String meal_name;
        private Boolean checked;
    }

    private Integer shop_id;
    private String shop_name;

    private List<Meal> mealList;

}
