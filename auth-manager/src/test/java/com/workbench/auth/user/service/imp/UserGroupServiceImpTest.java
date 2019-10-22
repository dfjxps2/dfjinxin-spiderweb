package com.workbench.auth.user.service.imp;

import com.AbstractTestService;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.workbench.auth.group.entity.Group;
import com.workbench.auth.user.service.UserGroupService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SongCQ on 2017/7/7.
 */
public class UserGroupServiceImpTest extends AbstractTestService{

    @Resource
    private UserGroupService userGroupService;

    @Test
    public void listGroupByUserId() throws Exception {
        List<Group> list = userGroupService.listGroupByUserId(1);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setResultData(list);
        System.out.println("user role is "+ JsonSupport.objectToJson(jsonResult));

    }

    @Test
    public void saveUserGroup() throws Exception {
        userGroupService.saveUserGroup(1,3);
        userGroupService.saveUserGroup(1,4);
        userGroupService.saveUserGroup(1,5);
    }

    @Test
    public void delUserGroup() throws Exception {
        userGroupService.delUserGroup(1,3);

    }

}