package com.workbench.auth.role.service.imp;

import com.github.pagehelper.Page;
import com.workbench.auth.role.dao.IRoleManageDao;
import com.workbench.auth.role.dao.IRoleMenuDao;
import com.workbench.auth.role.service.RoleManageService;
import com.workbench.auth.role.entity.Role;
import com.workbench.auth.user.dao.IUserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
@Service("roleManageService")
public class RoleManageServiceImp implements RoleManageService {

    @Autowired
    private IRoleManageDao roleManageDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Override
    public Page<Role> rolePageData(int currPage, int pageSize) {
        Page<Role> rolePageDataList = roleManageDao.rolePageData(currPage, pageSize);
        return rolePageDataList;
    }

    @Override
    public Role getRoleById(int role_id) {
        Role roleData = roleManageDao.getRoleById(role_id);
        return roleData;
    }

    @Override
    public void saveNewRole(Role role) {
        roleManageDao.saveNewRole(role);
        return;
    }

    @Override
    public void updateSaveRole(Role role) {
        roleManageDao.updateSaveRole(role);
        return;
    }

    @Override
    public void deleteRole(int user_role_id) {
        userRoleDao.delUserRoleByRoleId(user_role_id);
        roleMenuDao.delMenuByRoleId(user_role_id);
        roleManageDao.deleteRole(user_role_id);
    }
}
