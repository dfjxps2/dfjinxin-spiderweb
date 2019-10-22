package com.workbench.auth.role.dao;

import com.github.pagehelper.Page;
import com.workbench.auth.role.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
public interface IRoleManageDao {

    @Select("select * from user_role")
    @Options(useCache = false)
    Page<Role> rolePageData(@Param("currPage") int currPage, @Param("pageSize") int pageSize);

    @Select("select * from user_role where user_role_id=#{user_role_id}")
    @Options(useCache = false)
    Role getRoleById(int role_id);

    @Insert("insert into user_role (user_role_id,user_role_name) values (#{user_role_id},#{user_role_name})")
    @Options(useCache = false)
    void saveNewRole(Role role);

    @Update("update user_role set user_role_name=#{user_role_name} where user_role_id=#{user_role_id}")
    @Options(useCache = false)
    void updateSaveRole(Role role);

    @Delete("delete from user_role where user_role_id=#{user_role_id}")
    @Options(useCache = false)
    void deleteRole(int user_role_id);
}
