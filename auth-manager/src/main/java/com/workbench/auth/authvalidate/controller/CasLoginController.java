package com.workbench.auth.authvalidate.controller;

import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.session.SessionSupport;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.service.UserService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by SongCQ on 2018/6/14.
 */

@Controller
@RequestMapping("sys/casLogin")
public class CasLoginController extends AbstractLoginController{

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("doLogin")
    public void casLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String loginUserName = principal.getName();
        Map<String, Object> allAttributes = principal.getAttributes();
        logger.info("CAS登录重定向请求已收到.参数内容为{},用户名为【{}】", allAttributes.toString(), loginUserName);
        User user = new User();
        user.setUser_name(loginUserName);
        SessionSupport.addUserToSession(user);
        User usrFromDb = userService.getUserByUserNm(loginUserName);
        if(usrFromDb==null){
            userService.createUser(user);
        }

        String redirectUrl = request.getParameter("redirect");

        response.sendRedirect(redirectUrl);
    }

    @RequestMapping("checkCasLoginUser")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String checkCasLoginUser(){
        String responseJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, this.getLoginUserInfo());
        return responseJson;
    }

    @RequestMapping("doLogout")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String doLogout(){
        String responseJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "退出成功", null, this.getLoginUserInfo());
        return responseJson;
    }
}
