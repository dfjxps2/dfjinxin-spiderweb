package com.workbench.auth.user.controller;

import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonpSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.service.UserGroupService;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by SongCQ on 2017/7/7.
 */
@Controller
@RequestMapping("sys/userGroup")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @RequestMapping("listGroupByUserId")
    @ResponseBody
    @JsonpCallback
    public String listGroupByUserId(int user_id){
        List<Group> userGroupList = userGroupService.listGroupByUserId(user_id);
        String jsonResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, userGroupList);
        return jsonResult;
    }

    @RequestMapping("listUsersByGroupId")
    @ResponseBody
    @JsonpCallback
    public String listUsersByGroupId(int user_group_id,int currPage, int pageSize){
        Page<User> groupPage = userGroupService.listUsersByGroupId(user_group_id,currPage,pageSize);

        PageResult pageResult = PageResult.pageHelperList2PageResult(groupPage);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);
        return resultJson;
    }

    @RequestMapping("saveUserGroup")
    @ResponseBody
    @JsonpCallback
    public String saveUserGroup(int user_id,int user_group_id){
        userGroupService.saveUserGroup(user_id,user_group_id);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, null);
        return resultJson;
    }

    @RequestMapping("delUserGroup")
    @ResponseBody
    @JsonpCallback
    public String delUserGroup(int user_id,int user_group_id){
        userGroupService.delUserGroup(user_id,user_group_id);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, null);
        return resultJson;

    }
}
