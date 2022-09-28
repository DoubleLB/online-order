package org.lbw.story.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String u_id;
    private String u_pwd;
    private String u_nickname;
    private String u_avatar;
    private Date u_register;
}
