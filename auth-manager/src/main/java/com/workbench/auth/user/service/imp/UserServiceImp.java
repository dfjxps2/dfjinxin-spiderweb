package com.workbench.auth.user.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.webapp.support.encryption.MD5;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.entity.UserStatus;
import com.workbench.auth.user.service.UserService;
import com.workbench.auth.user.dao.IUserServiceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by pc on 2017/6/29.
 */
@Service("userService")
public class UserServiceImp implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    private String DEFAULT_PWD = "123456";

    @Autowired
    private IUserServiceDao userServiceDao;

    public User checkUser(String userNm, String password) {
        String md5Password = MD5.getMD5Value(password);

        User resultUser = userServiceDao.checkUserByNmAndPwd(userNm,md5Password);
        logger.debug("check user result {}",resultUser);
        return resultUser;
    }

    public List<User> listAllUser(){
        List<User> allUser = userServiceDao.listAllUser();
        logger.debug("check user result {}",allUser.toString());
        return allUser;
    }

    public Page<User> listUsersForPage(int currPage,int pageSize,User user,String originId){

        Page<User> allUser = userServiceDao.listUsersForPage(
                currPage,pageSize,
                user.getUser_id(),user.getUser_name(),
                user.getUser_type(),originId);

        return allUser;
    }

    public Page<User> pageUsers(int currPage,int pageSize,User user){

        Page<User> allUser = userServiceDao.pageUsers(
                currPage,pageSize,
                user.getUser_id(),user.getUser_name(),user.getUser_type());

        return allUser;
    }

    public User createUser(User user){
        SimpleDateFormat format = new SimpleDateFormat("ssSSS");
        StringBuilder builder = new StringBuilder();
        builder.append(format.format(Calendar.getInstance().getTime()));
        builder.append(new Random().nextInt(50));
        int user_id = new Integer(builder.toString());

        user_id = user_id<<(new Random().nextInt(5));
        user.setUser_id(user_id);
        if(Strings.isNullOrEmpty(user.getUser_pwd())){
            user.setUser_pwd(MD5.getMD5Value(DEFAULT_PWD));
        }
        user.setUser_status(String.valueOf(UserStatus.NORMAL.getStatus()));
        logger.debug("save user info {}",user);
        userServiceDao.saveNewUser(user);
        return user;
    }

    public void updateUser(User user){
        userServiceDao.updateSave(user);
    }

    public void resetPwd(Integer userId){
        userServiceDao.updatePwd(userId,MD5.getMD5Value(DEFAULT_PWD),String.valueOf(UserStatus.PWD_EXPIRED.getStatus()));
    }

    @Override
    public void changePwd(int userId, String userPwd) {
        userServiceDao.updatePwd(userId,MD5.getMD5Value(userPwd),String.valueOf(UserStatus.NORMAL.getStatus()));
    }

    public void delUserById(int user_id){
        userServiceDao.delUserById(user_id);
    }

    public User getUserByUserId(Integer userId){
        User resultUser = userServiceDao.getUserByUserId(userId);
        logger.debug(resultUser.toString());
        return resultUser;
    }

    public User getUserByUserNm(String userName){
        User resultUser = userServiceDao.checkUserByUserNm(userName);
        logger.debug(resultUser!=null?resultUser.toString():"user is null");
        return resultUser;
    }


    public List<Menu> getMenuList4User(String user_nm) {

        List<Menu> allMenuList = userServiceDao.getMenuList4User(user_nm);

        return allMenuList;
    }
}
