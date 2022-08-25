package cn.bishion.pier.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

/**
 * @author: init
 * @date: 2022-08-09
 * @version: 1.0.0
 */
@Mapper
public interface UserInfoMapper {
    @MapKey("id")
    List<Map> selectMap(@Param("username") String username);
    @Select("select * from user_info where username=#{username}")
    List<Map> selectMap1(@Param("username") String username);
}
