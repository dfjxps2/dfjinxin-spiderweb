package com.crawler.webapp.casclient.controller;

import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.session.SessionSupport;
import com.workbench.auth.authvalidate.bean.LoginResult;
import com.workbench.auth.authvalidate.controller.LoginController;
import com.workbench.auth.authvalidate.service.LoginService;
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
import java.util.Map;

/**
 * Created by SongCQ on 2018/6/14.
 */

@Controller
@RequestMapping("sys/cqNyCasLogin")
public class CqNyCasLoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("doLogin")
    public String casLogin(HttpServletRequest request){
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String loginUserName = principal.getName();
        Map<String, Object> allAttributes = principal.getAttributes();
        logger.info("CAS登录重定向请求已收到.参数内容为{},用户名为【{}】", allAttributes.toString(), loginUserName);
        User user = userService.getUserByUserNm(loginUserName);
        if(user==null){
            return JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "当前登录/操作的用户不存在",
                    LoginResult.LOGIN_RESULT.USERNM_NOT_FOUND.toString(), null);
        }else
            SessionSupport.addUserToSession(userService.getUserByUserNm(loginUserName));
        return "sys/main/main_page";
    }

    /**
     * 模拟单点登录，演示用
     * @param request
     * @return
     */
    @RequestMapping("mockLogin")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String mockLogin(HttpServletRequest request){
        SessionSupport.addUserToSession(userService.getUserByUserNm("admin"));
        return "1";
    }
}
