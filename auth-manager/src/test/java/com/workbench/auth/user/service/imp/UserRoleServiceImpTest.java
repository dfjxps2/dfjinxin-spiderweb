package com.workbench.auth.user.service.imp;

import com.AbstractTestService;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.workbench.auth.role.entity.Role;
import com.workbench.auth.user.entity.UserRole;
import com.workbench.auth.user.service.UserRoleService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by pc on 2017/7/6.
 */
public class UserRoleServiceImpTest extends AbstractTestService {

    @Resource
    private UserRoleService userRoleService;

    @Test
    public void getRoleByUserId(){
        List<Role> role = userRoleService.getRoleByUserId(1);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setResultData(role);
        System.out.println("user role is "+ JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void saveUserRole(){

        UserRole userRole = new UserRole();
        userRole.setUser_id(1);
        userRole.setUser_role_id(2);

        userRoleService.saveUserRole(userRole);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");
        System.out.println("user role is "+ JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void delUserRole(){

        UserRole userRole = new UserRole();
        userRole.setUser_id(1);
        userRole.setUser_role_id(2);

        userRoleService.delUserRole(userRole);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("删除成功");
        System.out.println( JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void upUserRole(){

        UserRole userRole = new UserRole();
        userRole.setUser_id(1);
        userRole.setUser_role_id(1);

        userRoleService.updateUserRole(userRole,2);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");
        System.out.println("user role is "+ JsonSupport.objectToJson(jsonResult));
    }
}