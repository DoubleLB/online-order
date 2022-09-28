package org.lbw.story.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lbw.story.user.model.User;
import org.lbw.story.user.dto.LoginDto;

@Mapper //该注解表示改接口的对象会被Spring管理，放入Spring容器中
public interface LoginDao {

    @Select("select u_id,u_nickname,u_sex,u_pwd from t_user where u_id = #{u_id} and u_pwd = #{u_pwd}")
    User findUserByIdAndPwd(LoginDto loginDto);
}
