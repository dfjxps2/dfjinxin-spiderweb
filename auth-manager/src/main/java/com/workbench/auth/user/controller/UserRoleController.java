package com.workbench.auth.user.controller;

import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonpSupport;
import com.webapp.support.jsonp.JsonResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.Role;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.entity.UserRole;
import com.workbench.auth.user.service.UserRoleService;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by pc on 2017/7/6.
 */

@Controller
@RequestMapping("sys/userRole")
public class UserRoleController {

    private Logger logger = LoggerFactory.getLogger(UserRoleController.class);


    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("getRoleByUserId")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String getRoleByUserId(int user_id){
        List<Role> role = userRoleService.getRoleByUserId(user_id);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, role);
        logger.debug("jsonResult information after delete :{}",resultJson);
        return resultJson;
    }

    @RequestMapping("saveUserRole")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String saveUserRole(UserRole userRole){
        userRoleService.saveUserRole(userRole);

        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

        logger.debug("jsonResult information after saveUserRole :{}",resultJson);


        return resultJson;
    }

    @RequestMapping("updateUserRole")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String updateUserRole(UserRole userRole,Integer old_user_role_id){
        if(old_user_role_id!=null){
            userRoleService.updateUserRole(userRole,old_user_role_id);
        }else{
            userRoleService.saveUserRole(userRole);
        }

        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

        logger.debug("jsonResult information after delete :{}",resultJson);


        return resultJson;
    }

    @RequestMapping("delUserRole")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String delUserRole(UserRole userRole){
        userRoleService.delUserRole(userRole);
        String resultJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, null);

        logger.debug("jsonResult information after delete :{}",resultJson);


        return resultJson;
    }
}
