package com.workbench.cas.login.filter;

import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SongCQ on 2018/9/27.
 */
public class WorkbenchAuthenticationRedirectStrategy implements AuthenticationRedirectStrategy {

    @Override
    /**
     * 重写CAS重定向逻辑，直接返回JSON报文，由前端界面控制跳转到登录界面
     * CAS 默认实现类为 DefaultAuthenticationRedirectStrategy
     * changed DefaultAuthenticationRedirectStrategy to WorkbenchAuthenticationRedirectStrategy
     */
    public void redirect(HttpServletRequest request, HttpServletResponse response, String potentialRedirectUrl) throws IOException {
        response.setStatus(200);

        //allow cors 允许跨域请求
        String originHost = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Origin",originHost);
        response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers","X-PINGOTHER, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials","true");

        //返回报文 包含重定向地址和状态码
        ServletOutputStream responseOutStream = response.getOutputStream();

        String responseJson = JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD,"CAS认证失败,重定向CAS验证服务器","FORWARD_CAS",potentialRedirectUrl);

        responseOutStream.write(responseJson.getBytes());
    }
}
