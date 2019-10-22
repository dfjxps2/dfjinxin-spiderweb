package com.workbench.auth.user.service.controller;

import com.AbstractTestController;
import com.workbench.auth.user.controller.UserController;
import com.workbench.auth.user.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pc on 2017/7/6.
 */
public class UserControllerTest extends AbstractTestController{

    @Autowired
    private UserController userController;

    @Test
    public void saveNewUser(){

    }

}
