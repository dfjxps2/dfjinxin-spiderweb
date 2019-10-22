package com.workbench.auth.role.service;

import com.github.pagehelper.Page;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.RoleMenu;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
public interface RoleMenuManageService {

    List<Menu> getMenuByRole(int role_id);

    Page<Menu> pagingMenuByRole(int role_id,int currPage,int pageSize);

    void saveMenuForRole(RoleMenu roleMenu);

    void delMenuFromRole(RoleMenu roleMenu);

    void delMenuByRoleId(int role_id);

    List<Menu> getMenuOutRole(int user_role_id);

    void saveMenusForRole(Integer user_role_id,List<Integer> menus);
}
