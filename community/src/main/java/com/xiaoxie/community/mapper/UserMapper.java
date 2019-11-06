package com.xiaoxie.community.mapper;

import com.xiaoxie.community.model.User;
import com.xiaoxie.community.model.UserExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    int insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
}