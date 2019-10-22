package com.workbench.auth.group.service.imp;

import com.AbstractTestService;
import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.group.service.GroupService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by SongCQ on 2017/7/7.
 */
public class GroupServiceImpTest extends AbstractTestService {

    @Resource
    private GroupService groupService;

    @Test
    public void listUserGroupPage() throws Exception {
        Page<Group> page = groupService.listUserGroupPage(2, 8);
        PageResult pageResult = PageResult.pageHelperList2PageResult(page);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setResultData(pageResult);
        System.out.println("user role is "+ JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void getUserGroup() throws Exception {
        Group groupData = groupService.getUserGroup(2);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setResultData(groupData);
        System.out.println("user role is "+ JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void saveNewUserGroup() throws Exception {
        for(int i=1;i<18;i++){

            Group group = new Group();
            group.setSuper_user_group_id(0);
            group.setUser_group_id(i);
            group.setUser_group_name("测试组"+i);
            groupService.saveNewUserGroup(group);
        }
    }

    @Test
    public void updateUserGroup() throws Exception {
        for(int i=1;i<18;i++){

            Group group = new Group();
            group.setSuper_user_group_id(0);
            group.setUser_group_id(i);
            group.setUser_group_name("测试组--"+i);
            groupService.updateUserGroup(group);
        }
    }

    @Test
    public void delUserGroup() throws Exception {
        for(int i=1;i<18;i++){
            groupService.delUserGroup(i);
        }
    }

}