package com.workbench.auth.group.controller;

import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonpSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.group.service.GroupService;
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
@RequestMapping("sys/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping("listUserGroupPage")
    @ResponseBody
    @JsonpCallback
    public String listUserGroupPage(int currPage, int pageSize){
        Page<Group> userGrpPage = groupService.listUserGroupPage(currPage, pageSize);
        PageResult pageResult = PageResult.pageHelperList2PageResult(userGrpPage);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);

//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("listUserGroup")
    @ResponseBody
    @JsonpCallback
    public String listUserGroupPage(){
        Page<Group> userGrpPage = groupService.listUserGroupPage(1, 200);
        PageResult pageResult = PageResult.pageHelperList2PageResult(userGrpPage);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);

//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("listSuperGroup")
    @ResponseBody
    @JsonpCallback
    public String listSuperGroup(){
        List<Group> list = groupService.listSuperGroup();
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, list);

//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("getUserGroup")
    @ResponseBody
    @JsonpCallback
    public String  getUserGroup(int user_group_id){
        Group group = groupService.getUserGroup(user_group_id);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, group);
//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("saveNewGroup")
    @ResponseBody
    @JsonpCallback
    public String  saveNewUserGroup(Group group){
        groupService.saveNewUserGroup(group);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("updateUserGroup")
    @ResponseBody
    @JsonpCallback
    public String  updateUserGroup(Group group){
        groupService.updateUserGroup(group);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("delUserGroup")
    @ResponseBody
    @JsonpCallback
    public String  delUserGroup(int user_group_id){
        groupService.delUserGroup(user_group_id);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, null);

//        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

}
