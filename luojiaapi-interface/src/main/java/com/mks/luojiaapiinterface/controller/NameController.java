package com.mks.luojiaapiinterface.controller;

import com.mks.luojiaapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.mks.luojiaapiclientsdk.utils.SignUtil.getSign;


@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // todo 从数据库获取accessKey
        if(!accessKey.equals("yupi")) {
            throw new RuntimeException("无权限");
        }
        if((Integer.parseInt(nonce) > 9999)) {
            throw new RuntimeException("随机数错误");
        }
        // todo 校验时间戳timestamp
        // todo 从数据库获取secretKey
        String serverSign = getSign(body, "abcdefgh");
        if(!sign.equals(serverSign)){
            throw new RuntimeException("签名错误");
        }
        String result = "POST 用户名字是" + user.getUsername();
        //调用成功后，次数+1
        return result;
    }

}
