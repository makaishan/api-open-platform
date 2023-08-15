package com.mks.luojiaapiclientsdk;

import com.mks.luojiaapiclientsdk.client.LuojiaApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("luojiaapi.client")
@Data
@ComponentScan
public class LuojiaApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public LuojiaApiClient luojiaApiClient() {
        return new LuojiaApiClient(accessKey, secretKey);
    }
}
