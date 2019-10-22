package com.workbench.auth.user.service.imp;

import com.AbstractTestService;
import com.github.pagehelper.Page;
import com.webapp.support.encryption.MD5;
import com.webapp.support.jsonp.JsonpSupport;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.user.service.UserService;
import com.workbench.auth.user.entity.User;
import org.junit.Test;

import javax.annotation.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/6/30.
 */
public class UserServiceImpTest extends AbstractTestService {

    @Resource
    private UserService userService;

    @Test
    public void checkUser() throws Exception {

    }


    @Test
    public void listAllUser() throws Exception {
        userService.listAllUser();
    }

    @Test
    public void listUsersForPage(){
        User user = new User();
//        user.setUser_name("sc");
        Page<User> result = userService.listUsersForPage(1, 8,user,"5001001");
    }

    @Test
    public void createAndDelUser() throws Exception {
        List<User> saveuserList = new ArrayList();
        for(int i=0;i<20;i++){
            User user = new User();
            user.setUser_name("Test"+i);
            user.setUser_status("1");
            user.setUser_type("1");
            userService.createUser(user);
            saveuserList.add(user);
        }

//        for(User user:saveuserList){
//            userService.delUserById(user.getUser_id());
//        }

    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setUser_id(1);
        user.setUser_name("scq-n2");
        user.setUser_type("1");
        user.setUser_status("1");

        userService.updateUser(user);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void delUser() throws Exception {

    }

    @Test
    public void getUserByUserId() throws Exception {

    }

    @Test
    public void getUserByUserNm() throws Exception {

    }


    @Test
    public void getMenuList4User() throws Exception {
        List<Menu> result = userService.getMenuList4User("scq");
        System.out.print(result.toString());
    }

}