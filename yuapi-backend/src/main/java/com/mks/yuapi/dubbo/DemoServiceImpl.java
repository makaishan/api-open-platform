package com.mks.yuapi.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
public class DemoServiceImpl implements DemoService{
    @Override
    public String sayHello(String name) {
        return name + "你好";
    }
}
