package com.workbench.auth.menu.service.imp;

import com.github.pagehelper.Page;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.dao.IMenuDao;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.RoleMenu;
import com.workbench.auth.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pc on 2017/7/3.
 */
@Service("menuService")
public class MenuServiceImp implements MenuService{

    @Autowired
    private IMenuDao menuDao;


    public PageResult listMenuByPage(int currPage, int pageSize) {
        Page<Menu> menuPage = menuDao.listMenuByPage(currPage,pageSize);
        PageResult pageResult = PageResult.pageHelperList2PageResult(menuPage);
        return pageResult;
    }

    public Menu getMenu(int module_id){
        return menuDao.getMenu(module_id);
    }

    @Override
    public void saveNewMenu(Menu menu) {
        menuDao.saveNewMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDao.updateMenu(menu);
    }

    @Override
    public void delMenuById(int menu_id) {
        menuDao.delMenuById(menu_id);
    }

}
