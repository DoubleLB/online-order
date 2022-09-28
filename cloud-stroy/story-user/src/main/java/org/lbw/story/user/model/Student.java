package org.lbw.story.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //该注解会导致在编译时自动生成get和set方法
@NoArgsConstructor //该注解会导致在编译时自动生成无参构造方法
@AllArgsConstructor //该注解会导致在编译时自动生成全参构造方法
public class Student {

    private String id;
    private String name;
    private String sex;
    private Double score;

}
