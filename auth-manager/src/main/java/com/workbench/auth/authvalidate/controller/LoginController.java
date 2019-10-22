package com.workbench.auth.authvalidate.controller;

import com.google.common.base.Strings;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.session.SessionSupport;
import com.workbench.auth.authvalidate.service.LoginService;
import com.workbench.auth.authvalidate.bean.LoginResult;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.service.UserService;
import com.workbench.exception.runtime.UserNotFoundException;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pc on 2017/6/30.
 */

@Controller
@RequestMapping("sys/login")
public class LoginController extends AbstractLoginController{

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="doLogin",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    @JsonpCallback
    public String doLogin( String user_name, String user_pwd){
        boolean checkResult = Strings.isNullOrEmpty(user_name);
        if(checkResult){
            return JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "用户名为空", LoginResult.LOGIN_RESULT.USERNM_NOT_NULL.toString(), null);

        }else{
            LoginResult loginResult = loginService.validate(user_name, user_pwd);
            if(loginResult.getResult_code()!=LoginResult.LOGIN_RESULT.SUCCESS){
                if(LoginResult.LOGIN_RESULT.PWD_EXPIRED.equals(loginResult.getResult_code())){
                    this.addUserToSession(user_name);
                    return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "登陆成功,密码过期需要修改",
                            null, LoginResult.LOGIN_RESULT.PWD_EXPIRED.toString());
                }

                return JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, loginResult.getValidate_result(),
                        loginResult.getResult_code().toString(), null);
            }

        }
        this.addUserToSession(user_name);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "登录成功",null, "LOGIN_SUCCESS");
    }

    private void addUserToSession(String user_name) {
        User user = userService.getUserByUserNm(user_name);
        if(user==null){
           throw new UserNotFoundException(new StringBuilder().append("用户").append(user_name).append("未找到").toString());
        }else
            SessionSupport.addUserToSession(userService.getUserByUserNm(user_name));
    }

    @RequestMapping(value="loginRest",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public JsonResult loginRest(String user_name, String user_pwd){
        boolean checkResult = Strings.isNullOrEmpty(user_name);
        if(checkResult){
            return JsonSupport.makeJsonpResult(JsonResult.RESULT.FAILD, "用户名为空", LoginResult.LOGIN_RESULT.USERNM_NOT_NULL.toString(), null);

        }else{
            LoginResult loginResult = loginService.validate(user_name, user_pwd);
            if(loginResult.getResult_code()!=LoginResult.LOGIN_RESULT.SUCCESS){
                return JsonSupport.makeJsonpResult(JsonResult.RESULT.FAILD, loginResult.getValidate_result(),
                        LoginResult.LOGIN_RESULT.USERNM_NOT_FOUND.toString(), null);
            }
            User user = userService.getUserByUserNm(user_name);
            if(user==null){
                return JsonSupport.makeJsonpResult(JsonResult.RESULT.FAILD, "当前登录/操作的用户不存在",
                        LoginResult.LOGIN_RESULT.USERNM_NOT_FOUND.toString(), null);
            }else
                SessionSupport.addUserToSession(userService.getUserByUserNm(user_name));
        }
        return JsonSupport.makeJsonpResult(JsonResult.RESULT.SUCCESS, "登录成功",null, null);
    }

    @RequestMapping("checkLoginUser")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String checkLoginUser(){
        String responseJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, this.getLoginUserInfo());
        return responseJson;
    }

    @RequestMapping("logout")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String logout() {
        SessionSupport.logoutUser();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        ServletContext requestContext = request.getServletContext();

        Boolean casSwitch = new Boolean(requestContext.getInitParameter("casSwitch"));
        logger.warn("cas swtich :{}",casSwitch);
        String responseJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"登出成功","SUCCESS",null);
        if(casSwitch){
            String potentialRedirectUrl = requestContext.getInitParameter("casLogoutRedirect");
            logger.warn("potentialRedirectUrl :{}",potentialRedirectUrl);
            responseJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"登出成功","FORWARD_CAS",potentialRedirectUrl);
        }
        return responseJson;
    }

}
