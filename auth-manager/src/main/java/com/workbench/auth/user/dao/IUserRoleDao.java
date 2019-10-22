package com.workbench.auth.user.dao;

import com.workbench.auth.role.entity.Role;
import com.workbench.auth.user.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by pc on 2017/7/6.
 */
public interface IUserRoleDao {

    @Select("SELECT r.* FROM user_role_assign ura " +
            "inner join user u on ura.user_id=u.user_Id and u.user_id=#{user_id} " +
            "inner join user_role r on ura.user_role_id=r.user_role_id")
    List<Role> getRoleByUserId(int user_id);

    @Insert("insert into user_role_assign (user_id,user_role_id) values (#{user_id},#{user_role_id})")
    @Options(useCache = false)
    void saveUserRole(UserRole userRole);

    @Delete("delete from user_role_assign where user_id=#{user_id} and user_role_id=#{user_role_id}")
    @Options(useCache = false)
    void delUserRole(UserRole userRole);

    @Delete("delete from user_role_assign where user_role_id=#{user_role_id}")
    @Options(useCache = false)
    void delUserRoleByRoleId(int user_role_id);

    @Update("update user_role_assign set user_role_id=#{user_role_id} where user_id=#{user_id} and user_role_id=#{old_user_role_id}")
    @Options(useCache = false)
    void updateUserRole(@Param("user_id")int user_id,@Param("user_role_id")int user_role_id,@Param("old_user_role_id")int  old_user_role_id);
}
