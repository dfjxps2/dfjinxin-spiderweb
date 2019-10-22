package com.workbench.auth.menu.controller;

import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.jsonp.JsonpSupport;
import com.webapp.support.page.PageResult;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.menu.service.MenuService;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SongCQ on 2017/7/7.
 */
@Controller
@RequestMapping("sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("listMenuByPage")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String listMenuByPage(int currPage, int pageSize){
        PageResult menuList = menuService.listMenuByPage(currPage, pageSize);

        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, menuList);

        return jsonpResponse;
    }

    @RequestMapping("getMenuById")
    @ResponseBody
    @JsonpCallback
    public String getMenuById(int module_id){
        Menu menu = menuService.getMenu(module_id);
        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, menu);

        return jsonpResponse;
    }

    @RequestMapping("saveNewMenu")
    @ResponseBody
    @JsonpCallback
    public String  saveNewMenu(Menu menu){

        menuService.saveNewMenu(menu);

        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

        return jsonpResponse;
    }

    @RequestMapping("updateMenu")
    @ResponseBody
    @JsonpCallback
    public String  updateMenu(Menu menu){
        menuService.updateMenu(menu);
        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);

        return jsonpResponse;
    }

    @RequestMapping("delMenuById")
    @ResponseBody
    @JsonpCallback
    public String  delMenuById(int module_id){
        menuService.delMenuById(module_id);
        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, null);
        return jsonpResponse;
    }

}
