package com.workbench.auth.user.controller;

import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.session.SessionSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.user.entity.UserStatus;
import com.workbench.auth.user.service.UserService;
import com.workbench.auth.user.entity.User;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by pc on 2017/6/30.
 */
@Controller
@RequestMapping("sys/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("listUserPage")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String getUserByPage(int currPage, int pageSize,User user, String userType,String originId){
        user.setUser_type(userType);
        Page<User> userPageList = userService.listUsersForPage(currPage, pageSize,user,originId);
        PageResult pageResult = PageResult.pageHelperList2PageResult(userPageList);

        String jsonResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);
        return jsonResult;
    }

    @RequestMapping("pageUsers")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String pageUsers(int currPage, int pageSize,User user, String userType){
        user.setUser_type(userType);
        Page<User> userPageList = userService.pageUsers(currPage, pageSize,user);
        PageResult pageResult = PageResult.pageHelperList2PageResult(userPageList);
        String jsonResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);
        return jsonResult;
    }

    @RequestMapping("listAllUser")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String listAllUser(){
        List<User> userList = userService.listAllUser();
        String jsonResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, userList);
        return jsonResult;
    }

    @RequestMapping("userMenuList")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String getMenuList4User(){
        User user = SessionSupport.checkoutUserFromSession();
        JsonResult jsonResult = JsonResult.getInstance();
        if(user!=null){
            List<Menu> allMenu = userService.getMenuList4User(user.getUser_name());
            jsonResult.setResult(JsonResult.RESULT.SUCCESS);
            jsonResult.setResult_msg("获取菜单数据成功");
            jsonResult.setResultData(allMenu);
        }else{
            jsonResult.setResult(JsonResult.RESULT.FAILD);
            jsonResult.setFaild_reason("USER_NOT_LOGIN");
            jsonResult.setResult_msg("用户未登录,请重新登录");
        }
//        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, jsonResult);

        logger.debug("get user menu list,result jsonp value :{}",jsonResult.toString());

        return jsonResult.toString();

    }


    @RequestMapping("saveNewUser")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String saveNewUser(User user){
        userService.createUser(user);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResultData(user);
        jsonResult.setResult_msg("保存成功");
        logger.debug("user bean information after create :{}, and json value is 【{}】",user.toString(),jsonResult.toString());

        return jsonResult.toString();
    }

    @RequestMapping("delUserByUserId")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String delUserByUserId(Integer user_id){
        userService.delUserById(user_id);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("删除成功");
        logger.debug("jsonResult information after delete :{}",jsonResult.toString());

        return jsonResult.toString();
    }

    @RequestMapping("getUserByUserId")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String getUserByUserId(Integer user_id){
        User user = userService.getUserByUserId(user_id);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setResultData(user);
        logger.debug("jsonResult information after delete :{}",jsonResult.toString());

        return jsonResult.toString();
    }

    @RequestMapping(value = "updateSaveUser")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String updateSaveUser(@RequestBody User user){
        userService.updateUser(user);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");
        logger.debug("jsonResult information after delete :{}",jsonResult.toString());

        return jsonResult.toString();
    }

    @RequestMapping(value = "changePwd")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public JsonResult changePwd(@RequestBody User user){
        String userName = user.getUser_name();
        User userDb = userService.getUserByUserNm(userName);
        String userPwd = user.getUser_pwd();

        userService.changePwd(userDb.getUser_id(),userPwd);

        User userFromSession = SessionSupport.checkoutUserFromSession();
        userFromSession.setUser_status(String.valueOf(UserStatus.NORMAL.getStatus()));

        JsonResult jsonResult = JsonSupport.makeJsonpResult(JsonResult.RESULT.SUCCESS, "修改成功",
                null, JsonResult.RESULT.SUCCESS.toString());

        return jsonResult;
    }
}
