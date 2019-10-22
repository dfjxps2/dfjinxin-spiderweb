package com.workbench.auth.group.service;

import com.github.pagehelper.Page;
import com.workbench.auth.group.entity.Group;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
public interface GroupService {

    Page<Group> listUserGroupPage(int currPage, int pageSize);

    List<Group> listSuperGroup();

    void saveNewUserGroup(Group group);

    void updateUserGroup(Group group);

    void delUserGroup(int user_group_id);

    Group getUserGroup(int user_group_id);
}
