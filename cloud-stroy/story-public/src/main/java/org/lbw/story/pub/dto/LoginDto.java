package org.lbw.story.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String u_id;
    private String u_pwd;
    private String u_valid;
    private String key;
}
