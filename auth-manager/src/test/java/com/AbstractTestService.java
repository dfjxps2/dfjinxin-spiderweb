package com;

import com.workbench.auth.user.entity.User;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={
        "classpath*:applicationContext.xml"
        ,
        "classpath*:data-source-context.xml",
        "classpath*:sqlMapConfig.xml"
})
public class AbstractTestService {
}