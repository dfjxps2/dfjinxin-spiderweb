package com.workbench.auth.user.service;

import com.github.pagehelper.Page;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.user.entity.User;

import java.util.List;

/**
 * Created by pc on 2017/6/29.
 */
public interface UserService {

    User checkUser(String userNm, String password);

    List<User> listAllUser();

    Page<User> listUsersForPage(int currPage, int pageSize,User user,String originId);

    User createUser(User user);

    void updateUser(User user);

    void delUserById(int user_id);

    User getUserByUserId(Integer userId);

    User getUserByUserNm(String userName);

    List<Menu> getMenuList4User(String user_nm);
    Page<User> pageUsers(int currPage,int pageSize,User user);
    void resetPwd(Integer userId);


    void changePwd(int userId, String userPwd);
}
