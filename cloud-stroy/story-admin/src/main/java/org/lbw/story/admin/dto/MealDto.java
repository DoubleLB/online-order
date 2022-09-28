package org.lbw.story.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MealDto {

    private Integer meal_id;
    private String meal_name;
    private Integer cls_id;
    private Double meal_price;
    private String meal_desc;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss" )//非json格式接受参数
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss" ,timezone = "GMT+8")//接受json格式的参数
    private Date insert_time;//这里接受页面传来的参数（2022-05-23  17:20:30），但是不识别格式，需要通过注解说明时间格式@DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss" )
    private String meal_pic;
}
