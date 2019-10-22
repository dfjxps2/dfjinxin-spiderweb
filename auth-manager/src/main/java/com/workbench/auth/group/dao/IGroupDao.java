package com.workbench.auth.group.dao;

import com.github.pagehelper.Page;
import com.workbench.auth.group.entity.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
public interface IGroupDao {

    @Select("select * from user_group")
    @Options(useCache = false)
    Page<Group> listUserGroupPage(@Param("currPage") int currPage, @Param("pageSize") int pageSize);

    @Insert("insert into user_group (user_group_id,user_group_name,super_user_group_id) " +
            "values (#{user_group_id},#{user_group_name},#{super_user_group_id})")
    @Options(useCache = false)
    void saveNewUserGroup(Group group);

    @Update("update user_group set user_group_name=#{user_group_name},super_user_group_id=#{super_user_group_id} " +
            "where user_group_id=#{user_group_id}")
    @Options(useCache = false)
    void updateUserGroup(Group group);

    @Delete("delete from user_group where user_group_id=#{user_group_id}")
    @Options(useCache = false)
    void delUserGroup(int user_group_id);

    @Select("select * from user_group where user_group_id=#{user_group_id}")
    @Options(useCache = false)
    Group getUserGroup(int user_group_id);

    @Select("select * from user_group where super_user_group_id=0")
    @Options(useCache = false)
    List<Group> listSuperGroup();
}
