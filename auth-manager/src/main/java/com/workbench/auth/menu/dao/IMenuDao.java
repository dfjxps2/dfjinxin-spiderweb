package com.workbench.auth.menu.dao;

import com.github.pagehelper.Page;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.RoleMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by pc on 2017/7/3.
 */
public interface IMenuDao {

    @Select("select * from app_module")
    @Options(useCache = false)
    Page<Menu> listMenuByPage(@Param("currPage") int currPage,@Param("pageSize") int pageSize);

    @Insert("insert into app_module (module_id,super_module_id,module_name) values " +
            "(#{module_id},#{super_module_id},#{module_name})")
    @Options(useCache = false)
    void saveNewMenu(Menu menu);

    @Update("update app_module set super_module_id=#{super_module_id},module_name=#{module_name}" +
            " where module_id = #{module_id}")
    @Options(useCache = false)
    void updateMenu(Menu menu);

    @Delete("delete from app_module  where module_id = #{module_id}")
    @Options(useCache = false)
    void delMenuById(int module_id);

    @Select("select * from app_module where module_id = #{module_id}")
    @Options(useCache = false)
    Menu getMenu(int module_id);
}
