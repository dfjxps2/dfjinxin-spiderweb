package com.workbench.auth.role.controller;

import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.role.entity.Role;
import com.workbench.auth.role.service.RoleManageService;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongCQ on 2017/7/6.
 */
@Controller
@RequestMapping("sys/role")
public class RoleManageController {

    Logger logger = LoggerFactory.getLogger(RoleManageController.class);

    @Autowired
    private RoleManageService roleManageService;

    @RequestMapping("rolePageData")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String rolePageData(int currPage, int pageSize){
        Page<Role> pageList = roleManageService.rolePageData(currPage, pageSize);
        PageResult pageResult = PageResult.pageHelperList2PageResult(pageList);

        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);

        logger.debug("rolePageData value is {}",jsonpResponse);

        return jsonpResponse;
    }

    @RequestMapping("getRoleById")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String getRoleById(int user_role_id){
        Role roleData = roleManageService.getRoleById(user_role_id);
        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, roleData);

        logger.debug("getRoleById value is {}",jsonpResponse);

        return jsonpResponse;
    }

    @RequestMapping("saveNewRole")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String saveNewRole(@RequestBody Role role){
        roleManageService.saveNewRole(role);
        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

        logger.debug("saveNewRole value is {}",jsonpResponse);

        return jsonpResponse;
    }

    @RequestMapping("updateSaveRole")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updateSaveRole(@RequestBody Role role){
        roleManageService.updateSaveRole(role);
        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

        logger.debug("updateSaveRole value is {}",jsonpResponse);

        return jsonpResponse;
    }

    @RequestMapping("deleteRole")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String deleteRole(int user_role_id){
        roleManageService.deleteRole(user_role_id);

        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, null);

        logger.debug("deleteRole value is {}",jsonpResponse);

        return jsonpResponse;
    }
}
