package com.workbench.auth.user.service;

import com.github.pagehelper.Page;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.user.entity.User;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
public interface UserGroupService {

    List<Group> listGroupByUserId(int user_id);

    void saveUserGroup(int user_id,int user_group_id);

    void delUserGroup(int user_id,int user_group_id);

    Page<User> listUsersByGroupId(int group_id, int currPage, int pageSize);
}
