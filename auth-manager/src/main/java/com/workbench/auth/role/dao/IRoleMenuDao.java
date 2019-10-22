package com.workbench.auth.role.dao;

import com.github.pagehelper.Page;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.RoleMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
public interface IRoleMenuDao {

    @Select("select am.* from user_role_privilege urp inner join app_module am on " +
            "urp.module_id = am.module_id and urp.user_role_id = #{user_role_id}")
    @Options(useCache = false)
    List<Menu> getMenuByRole(int user_role_id);

    @Insert("insert into user_role_privilege (user_role_id,module_id) values (#{user_role_id},#{module_id})")
    @Options(useCache = false)
    void saveMenuForRole(RoleMenu roleMenu);

    @Update("delete from user_role_privilege where user_role_id=#{user_role_id} and module_id=#{module_id}")
    @Options(useCache = false)
    void delMenuFromRole(RoleMenu roleMenu);

    @Delete("delete from user_role_privilege where user_role_id=#{user_role_id}")
    @Options(useCache = false)
    void delMenuByRoleId(int role_id);

    @Select("select am.* from user_role_privilege urp inner join app_module am on " +
            "urp.module_id = am.module_id and urp.user_role_id = #{user_role_id}")
    @Options(useCache = false)
    Page<Menu> pagingMenuByRole(@Param("user_role_id") int user_role_id, @Param("currPage")  int currPage, @Param("pageSize")  int pageSize);

    @Select("select result.* from ( " +
            "SELECT urp.user_role_id,am.* FROM user_role_privilege urp right join app_module am on urp.user_role_id=#{user_role_id} and urp.module_id = am.module_id ) " +
            "result " +
            "where result.user_role_id is null;")
    @Options(useCache = false)
    List<Menu> getMenuOutRole(int user_role_id);
}
