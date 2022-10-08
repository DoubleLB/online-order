package org.lbw.story.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lbw.story.user.dto.UserDto;
import org.lbw.story.user.model.User;

@Mapper
public interface UserDao {
    @Select("select * from t_user where u_id = #{id}")
    User getUserInfo(String id);

    @Update("UPDATE t_user SET u_nickname = #{u_nickname} , u_pwd = #{u_pwd} , u_avatar = #{u_avatar} , u_sex = #{u_sex} WHERE u_id = #{u_id}")
    void UpdateUserInfo(UserDto userDto);

}
