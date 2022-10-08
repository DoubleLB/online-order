package org.lbw.story.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String u_id;
    private String u_pwd;
    private String u_nickname;
    private String u_avatar;
    private String u_sex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date u_register;
}
