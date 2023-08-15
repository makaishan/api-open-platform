package com.mks.luojiaapi.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService{
    @Override
    public String sayHello(String name) {
        return name + "你好";
    }
}
