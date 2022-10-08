package org.lbw.story.pub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String u_id;
    private String u_pwd;
    private String u_nickname;
    private String u_sex;
    private String u_avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date u_register;

    
}
