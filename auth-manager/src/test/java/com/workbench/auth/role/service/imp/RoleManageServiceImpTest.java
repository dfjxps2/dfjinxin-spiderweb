package com.workbench.auth.role.service.imp;

import com.AbstractTestService;
import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.role.service.RoleManageService;
import com.workbench.auth.role.entity.Role;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/6.
 */
public class RoleManageServiceImpTest extends AbstractTestService {

    @Resource
    private RoleManageService roleManageService;

    @Test
    public void rolePageData() throws Exception {
        Page<Role> result = roleManageService.rolePageData(2, 8);
        PageResult pageResult = PageResult.pageHelperList2PageResult(result);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setFaild_reason(null);
        jsonResult.setResultData(pageResult);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void getRoleById() throws Exception {
        Role role = roleManageService.getRoleById(3);
        System.out.println(role);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setFaild_reason(null);
        jsonResult.setResultData(role);
        System.out.println(JsonSupport.objectToJson(jsonResult));

    }

    @Test
    public void saveNewRole() throws Exception {
        for(int i=4;i<14;i++)
        {
            Role role = new Role();
            role.setUser_role_id(i);
            role.setUser_role_name("测试角色"+i);
            roleManageService.saveNewRole(role);
        }
    }

    @Test
    public void updateSaveRole() throws Exception {
        Role role = new Role();
        role.setUser_role_id(2);
        role.setUser_role_name("测试角色2333");
        roleManageService.updateSaveRole(role);
    }

}