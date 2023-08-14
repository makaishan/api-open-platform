package com.mks.yuapiinterface;

import com.mks.yuapiclientsdk.client.YuApiClient;
import com.mks.yuapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class YuapiInterfaceApplicationTests {


    @Resource
    private YuApiClient yuApiClient;

    @Test
    void contextLoads() {
        String nameByGet = yuApiClient.getNameByGet("yupi");
        User user = new User();
        user.setUsername("mks");
        String usernameByPost = yuApiClient.getUsernameByPost(user);
        System.out.println(nameByGet);
        System.out.println(usernameByPost);
    }

}
