package com.workbench.auth.menu.service;

import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.RoleMenu;

import java.util.List;

/**
 * Created by pc on 2017/7/3.
 */
public interface MenuService {

    PageResult listMenuByPage(int currPage, int pageSize);

    void saveNewMenu(Menu menu);

    void updateMenu(Menu menu);

    void delMenuById(int menu_id);

    Menu getMenu(int module_id);

}
