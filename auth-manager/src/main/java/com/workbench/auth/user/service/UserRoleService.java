package com.workbench.auth.user.service;

import com.workbench.auth.role.entity.Role;
import com.workbench.auth.user.entity.UserRole;

import java.util.List;

/**
 * Created by pc on 2017/7/6.
 */
public interface UserRoleService {

    List<Role> getRoleByUserId(int user_id);

    void saveUserRole(UserRole userRole);

    void updateUserRole(UserRole userRole,int old_user_role_id);

    void delUserRole(UserRole userRole);

    void getUserByRoleId(int user_role_id);
}
