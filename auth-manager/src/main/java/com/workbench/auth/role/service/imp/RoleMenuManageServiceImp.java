package com.workbench.auth.role.service.imp;

import com.github.pagehelper.Page;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.dao.IRoleMenuDao;
import com.workbench.auth.role.entity.RoleMenu;
import com.workbench.auth.role.service.RoleMenuManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
@Service("roleMenuManageService")
public class RoleMenuManageServiceImp implements RoleMenuManageService{

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Override
    public List<Menu> getMenuByRole(int role_id) {
        return roleMenuDao.getMenuByRole(role_id);
    }

    @Override
    public Page<Menu> pagingMenuByRole(int role_id, int currPage, int pageSize) {
        Page<Menu> result = roleMenuDao.pagingMenuByRole(role_id, currPage, pageSize);
        return roleMenuDao.pagingMenuByRole(role_id,currPage,pageSize);
    }


    @Override
    public void saveMenuForRole(RoleMenu roleMenu) {
        roleMenuDao.saveMenuForRole(roleMenu);
    }

    @Override
    public void delMenuFromRole(RoleMenu roleMenu) {
        roleMenuDao.delMenuFromRole(roleMenu);
    }

    @Override
    public void delMenuByRoleId(int role_id) {
        roleMenuDao.delMenuByRoleId(role_id);
    }

    @Override
    public List<Menu> getMenuOutRole(int user_role_id) {
        return roleMenuDao.getMenuOutRole(user_role_id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMenusForRole(Integer user_role_id, List<Integer> menus) {
        roleMenuDao.delMenuByRoleId(user_role_id);
        for(Integer menu : menus){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setUser_role_id(user_role_id);
            roleMenu.setModule_id(menu);
            roleMenuDao.saveMenuForRole(roleMenu);
        }
    }
}
