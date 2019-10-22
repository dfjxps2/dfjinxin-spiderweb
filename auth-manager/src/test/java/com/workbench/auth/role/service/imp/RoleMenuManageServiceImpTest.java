package com.workbench.auth.role.service.imp;

import com.AbstractTestService;
import com.google.common.collect.Lists;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.role.entity.RoleMenu;
import com.workbench.auth.role.service.RoleMenuManageService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SongCQ on 2017/7/6.
 */
public class RoleMenuManageServiceImpTest extends AbstractTestService {

    @Resource
    private RoleMenuManageService roleMenuManageService;

    @Test
    public void getMenuByRole() throws Exception {
        List<Menu> list = roleMenuManageService.getMenuByRole(1);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setFaild_reason(null);
        jsonResult.setResultData(list);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void saveMenuForRole() throws Exception {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setUser_role_id(1);
        roleMenu.setModule_id(5);
        roleMenuManageService.saveMenuForRole(roleMenu);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");
        jsonResult.setFaild_reason(null);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void delMenuFromRole() throws Exception {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setUser_role_id(1);
        roleMenu.setModule_id(5);
        roleMenuManageService.delMenuFromRole(roleMenu);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("删除成功");

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void modifyMenuForRole(){
        int user_role_id = 1;
        roleMenuManageService.delMenuByRoleId(user_role_id);

        ArrayList<Integer> moduleList = Lists.newArrayList();
        moduleList.add(1);
        moduleList.add(2);
        moduleList.add(3);
        for(Integer model_id:moduleList){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setModule_id(model_id);
            roleMenu.setUser_role_id(user_role_id);
            roleMenuManageService.saveMenuForRole(roleMenu);
        }

    }

}