package com.webapp.support.session;

import com.workbench.exception.runtime.NotLoginException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pc on 2017/7/3.
 */
public class SessionSupport {

    public static <T>T checkoutUserFromSession(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return checkoutUserFromSession(request);
    }

    public static <T>T checkoutUserFromSession(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute("user");
        if(userObj!=null)
            return (T) userObj;
        else{
            throw new NotLoginException("当前用户未登陆");
        }
    }

    public static void addDataToSession(HttpServletRequest request,Object object,String dataName){
        request.getSession().setAttribute(dataName,object);
    }

    public static void addUserToSession(HttpServletRequest request,Object user){
        addDataToSession(request,user,"user");
    }

    public static void addUserToSession(Object user){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        addDataToSession(request,user,"user");
    }


    public static void logoutUser()  {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        try {//working in servlet 3.0
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
