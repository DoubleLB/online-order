package org.lbw.story.user.dto;


import lombok.Data;

@Data
public class UserDto {
    private String u_id;
    private String u_pwd;
    private String u_nickname;
    private String u_avatar;
    private String u_sex;
}
