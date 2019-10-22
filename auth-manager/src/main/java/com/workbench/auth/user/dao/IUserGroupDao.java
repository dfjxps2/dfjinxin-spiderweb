package com.workbench.auth.user.dao;

import com.github.pagehelper.Page;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
public interface IUserGroupDao {

    @Select("select ug.* from user_group_member ugm inner join user_group ug on " +
            "ugm.user_group_id=ug.user_group_id and ugm.user_id=#{user_id}")
    @Options(useCache = false)
    List<Group> listGroupByUserId(int user_id);

    @Insert("insert into user_group_member (user_id,user_group_id) values (#{user_id},#{user_group_id})")
    @Options(useCache = false)
    void saveUserGroup(@Param("user_id") int user_id,@Param("user_group_id")  int user_group_id);

    @Delete("delete from user_group_member where user_id=#{user_id} and user_group_id=#{user_group_id}")
    @Options(useCache = false)
    void delUserGroup(@Param("user_id") int user_id,@Param("user_group_id")  int user_group_id);

    @Select("SELECT distinct u.* FROM spider_db.user_group_member ugm inner join user u on ugm.user_id=u.user_id and ugm.user_group_id=#{group_id}")
    Page<User> listUsersByGroupId(@Param("group_id")  int group_id,@Param("currPage")  int currPage,@Param("pageSize")  int pageSize);
}
