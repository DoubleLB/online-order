package org.lbw.story.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AdminOrder {

    private Integer order_id;
    private String u_nickname;
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss",timezone = "GMT+8")//设置时间格式和时区
    private Date order_submit;
    private Integer order_amount;
    private Integer order_status; //0 保存 1 已提交 2 已取消 3 已完成

    private String meal_name;
    private Integer meal_price;
    private String meal_pic;

    private Integer od_num;
}
