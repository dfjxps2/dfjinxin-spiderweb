package com.workbench.auth.role.service;

import com.github.pagehelper.Page;
import com.workbench.auth.role.entity.Role;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
public interface RoleManageService {

    Page<Role> rolePageData(int currPage, int pageSize);

    Role getRoleById(int role_id);

    void saveNewRole(Role role);

    void updateSaveRole(Role role);

    void deleteRole(int user_role_id);
}
