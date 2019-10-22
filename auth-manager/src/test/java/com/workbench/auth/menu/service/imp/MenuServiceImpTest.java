package com.workbench.auth.menu.service.imp;

import com.AbstractTestService;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.menu.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static java.awt.SystemColor.menu;
import static org.junit.Assert.*;

/**
 * Created by SongCQ on 2017/7/7.
 */
public class MenuServiceImpTest extends AbstractTestService {

    @Autowired
    private MenuService menuService;

    @Test
    public void listMenuByPage() throws Exception {
        PageResult resultMenu = menuService.listMenuByPage(1, 100);


        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setFaild_reason(null);
        jsonResult.setResultData(resultMenu);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void getMenuById() {
        Menu menu = menuService.getMenu(5);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("获取成功");
        jsonResult.setFaild_reason(null);
        jsonResult.setResultData(menu);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void saveNewMenu() throws Exception {
        for(int i=100000;i<100009;i++){
            Menu menu = new Menu();
            menu.setModule_id(i);
            menu.setSuper_module_id(0);
            menu.setModule_name("测试菜单"+i);
            menuService.saveNewMenu(menu);

        }

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");
        jsonResult.setFaild_reason(null);

        System.out.println(JsonSupport.objectToJson(jsonResult));

    }

    @Test
    public void updateMenu() throws Exception {
        for(int i=100000;i<100009;i++){
            Menu menu = new Menu();
            menu.setModule_id(i);
            menu.setSuper_module_id(0);
            menu.setModule_name("测试菜单---"+i);
            menuService.updateMenu(menu);

        }
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("保存成功");
        jsonResult.setFaild_reason(null);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

    @Test
    public void delMenuById() throws Exception {
        for(int i=100000;i<100009;i++){
//            Menu menu = new Menu();
//            menu.setModule_id(i);
//            menu.setSuper_module_id(0);
//            menu.setModule_name("测试菜单"+i);
            menuService.delMenuById(i);

        }

        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(JsonResult.RESULT.SUCCESS);
        jsonResult.setResult_msg("删除成功");
        jsonResult.setFaild_reason(null);

        System.out.println(JsonSupport.objectToJson(jsonResult));
    }

}