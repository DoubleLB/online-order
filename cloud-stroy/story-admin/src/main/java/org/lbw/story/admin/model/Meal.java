package org.lbw.story.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Meal {
    private Integer meal_id;
    private String meal_name;
    private Integer cls_id;
    private String cls_name;
    private String meal_desc;
    private Double meal_price;
    private String meal_pic;

    private boolean has;

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss",timezone = "GMT+8")//设置时间格式和时区
    private Date insert_time;
    private String insert_user;
}
