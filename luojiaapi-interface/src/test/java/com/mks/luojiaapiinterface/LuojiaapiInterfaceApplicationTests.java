package com.mks.luojiaapiinterface;

import com.mks.luojiaapiclientsdk.client.LuojiaApiClient;
import com.mks.luojiaapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class LuojiaapiInterfaceApplicationTests {


    @Resource
    private LuojiaApiClient luojiaApiClient;

    @Test
    void contextLoads() {
        String nameByGet = luojiaApiClient.getNameByGet("yupi");
        User user = new User();
        user.setUsername("mks");
        String usernameByPost = luojiaApiClient.getUsernameByPost(user);
        System.out.println(nameByGet);
        System.out.println(usernameByPost);
    }

}
