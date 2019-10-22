package com.workbench.auth.user.service.imp;

import com.workbench.auth.user.dao.IUserRoleDao;
import com.workbench.auth.role.entity.Role;
import com.workbench.auth.user.entity.UserRole;
import com.workbench.auth.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pc on 2017/7/6.
 */
@Service("userRoleService")
public class UserRoleServiceImp implements UserRoleService {

    @Autowired
    private IUserRoleDao iUserRoleDao;

    @Override
    public List<Role> getRoleByUserId(int user_id) {
        List<Role> role = iUserRoleDao.getRoleByUserId(user_id);
        return role;
    }

    @Override
    public void saveUserRole(UserRole userRole) {
        iUserRoleDao.saveUserRole(userRole);
    }

    @Override
    public void updateUserRole(UserRole userRole, int old_user_role_id) {
        iUserRoleDao.updateUserRole(userRole.getUser_id(),userRole.getUser_role_id(),old_user_role_id);
    }

    @Override
    public void delUserRole(UserRole userRole) {
        iUserRoleDao.delUserRole(userRole);
    }

    @Override
    public void getUserByRoleId(int user_role_id) {

    }
}
