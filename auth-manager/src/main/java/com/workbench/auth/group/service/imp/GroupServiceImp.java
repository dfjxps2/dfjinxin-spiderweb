package com.workbench.auth.group.service.imp;

import com.github.pagehelper.Page;
import com.workbench.auth.group.dao.IGroupDao;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
@Service("groupService")
public class GroupServiceImp implements GroupService {

    @Autowired
    private IGroupDao userGroupDao;

    @Override
    public Page<Group> listUserGroupPage(int currPage, int pageSize) {
        Page<Group> pageResult = userGroupDao.listUserGroupPage(currPage, pageSize);
        return pageResult;
    }

    @Override
    public List<Group> listSuperGroup() {
        return  userGroupDao.listSuperGroup();
    }

    @Override
    public void saveNewUserGroup(Group group) {
        userGroupDao.saveNewUserGroup(group);
    }

    @Override
    public void updateUserGroup(Group group) {
        userGroupDao.updateUserGroup(group);
    }

    @Override
    public void delUserGroup(int user_group_id) {
        userGroupDao.delUserGroup(user_group_id);
    }

    @Override
    public Group getUserGroup(int user_group_id) {
        return userGroupDao.getUserGroup(user_group_id);
    }
}
