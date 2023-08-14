package com.mks.yuapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mks.yuapiclientsdk.model.User;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

import static com.mks.yuapiclientsdk.utils.SignUtil.getSign;


/**
 * 调用第三方接口的客户端
 */
@Data
public class YuApiClient {

    private static final String GATEWAY_HOST = "http://localhost:8090";

    private String accessKey;
    private String secretKey;

    public YuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
    }


    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        return HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderBody(json))
                .body(json)
                .execute().body();
    }


    /**
     * 请求头
     * @param body body
     * @return Map
     */
    private Map<String, String> getHeaderBody(String body) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", getSign(body, secretKey));
        return hashMap;
    }


}
