package com.workbench.auth.user.service.imp;

import com.github.pagehelper.Page;
import com.workbench.auth.group.dao.IGroupDao;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.user.dao.IUserGroupDao;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
@Service("userGroupService")
public class UserGroupServiceImp implements UserGroupService{

    @Autowired
    private IUserGroupDao userGroupDao;

    @Override
    public List<Group> listGroupByUserId(int user_id) {
        return userGroupDao.listGroupByUserId(user_id);
    }

    @Override
    public void saveUserGroup(int user_id, int user_group_id) {
        userGroupDao.saveUserGroup( user_id,  user_group_id);
    }

    @Override
    public void delUserGroup(int user_id, int user_group_id) {
        userGroupDao.delUserGroup( user_id,  user_group_id);

    }

    @Override
    public Page<User> listUsersByGroupId(int group_id, int currPage, int pageSize) {
        Page<User> pageUser = userGroupDao.listUsersByGroupId(group_id, currPage, pageSize);
        return pageUser;
    }
}
