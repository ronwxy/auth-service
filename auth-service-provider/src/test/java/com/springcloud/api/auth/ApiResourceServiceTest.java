package com.springcloud.api.auth;

import com.springcloud.service.auth.service.biz.IApiResourceBiz;
import com.springcloud.service.auth.service.biz.vo.ApiResourcePermission;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiResourceServiceTest {

    @Autowired
    private IApiResourceBiz apiResourceBiz;

    @Test
    public void loadAllApiResourcePermissions_return_size(){
        Set<ApiResourcePermission> result = apiResourceBiz.loadAllApiResourcePermissions();
        Assertions.assertThat(result.size()).isEqualTo(10);
    }


}
