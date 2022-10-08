package org.lbw.story.admin.dao;

import org.apache.ibatis.annotations.*;
import org.lbw.story.admin.dto.ClsDto;
import org.lbw.story.admin.model.Cls;


import java.util.List;

@Mapper
public interface ClsDao {
    @Select("select * from t_cls")
    List<Cls> findClsList();

    @Insert("insert into t_cls(cls_name) values(#{cls_name})")
    void insertCls(ClsDto clsDto);

    @Update("update t_cls set cls_name = #{cls_name} where cls_id = #{cls_id}")
    void updateCls(ClsDto clsDto);

    @Delete("delete from t_cls where cls_id = #{cls_id}")
    void deleteCls(Integer cls_id);
}
