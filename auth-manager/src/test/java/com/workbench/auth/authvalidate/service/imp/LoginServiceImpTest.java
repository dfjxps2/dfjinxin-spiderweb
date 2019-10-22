package com.workbench.auth.authvalidate.service.imp;

import com.AbstractTestService;
import com.workbench.auth.authvalidate.service.LoginService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by pc on 2017/6/29.
 */
public class LoginServiceImpTest extends AbstractTestService{

    @Resource
    LoginService loginService ;

    @Test
    public void validate(){
        loginService.validate("scq","bb");
    }

}