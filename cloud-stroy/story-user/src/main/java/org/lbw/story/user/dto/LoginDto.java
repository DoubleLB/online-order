package org.lbw.story.user.dto;

/*
* dto包下对象一般用于封装客户端传来的数据
* mode包下对象一般用于封装查询数据库获得的对象
* */


import lombok.Data;

//封装登录请求数据
@Data
public class LoginDto {
    private String u_id;
    private String u_pwd;
}
