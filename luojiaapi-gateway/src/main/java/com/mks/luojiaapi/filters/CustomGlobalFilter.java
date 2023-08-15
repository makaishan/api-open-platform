package com.mks.luojiaapi.filters;

import com.mks.luojiaapiclientsdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "localhost");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.打印请求日志
        ServerHttpRequest request = exchange.getRequest();
        String requestId = request.getId();
        String requestPath = request.getPath().value();
        String requestMethod = Objects.requireNonNull(request.getMethod()).toString();
        String requestQueryParams = request.getQueryParams().toString();
        String requestCookies = request.getCookies().toString();
        String requestHostAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求id: " + requestId);
        log.info("请求路径: " + requestPath);
        log.info("请求方法: " + requestMethod);
        log.info("请求参数: " + requestQueryParams);
        log.info("请求Cookies: " + requestCookies);
        log.info("请求来源地址: " + requestHostAddress);
        // 2.访问控制: 黑白名单 --- 拒绝不在白名单中的地址访问
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(requestHostAddress)) {
            return handleNoAuth(response);
        }
        // 3.用户鉴权 --- 判断accessKey、secretKey等信息是否合法
        HttpHeaders requestHeader = request.getHeaders();
        String accessKey = requestHeader.getFirst("accessKey");
        String nonce = requestHeader.getFirst("nonce");
        String timestamp = requestHeader.getFirst("timestamp");
        String sign = requestHeader.getFirst("sign");
        String body = requestHeader.getFirst("body");
        // todo 从数据库获取accessKey，校验accessKey
        if (accessKey == null || !accessKey.equals("yupi")) {
            return handleNoAuth(response);
        }
        // 校验随机数nonce, 不能超过10000
        if (nonce == null || Long.parseLong(nonce) >= 10000L) {
            return handleNoAuth(response);
        }
        // 校验时间戳timestamp, 不能超过当前时间5分钟
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 60 * 5L;
        if (timestamp == null || ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES)) {
            return handleNoAuth(response);
        }
        // todo 从数据库获取secretKey，校验secretKey
        String serverSign = SignUtil.getSign(body, "abcdefgh");
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }
        // 4.判断用户调用的接口是否存在
        // todo 从数据库中查询接口是否存在 (可以通过微服务间远程调用的方式）
        // 5.请求转发, 调用接口
        return handleResponse(exchange, chain);
    }

    /**
     * 处理响应
     *
     * @param exchange exchange
     * @param chain    chain
     * @return Mono<Void>
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 装饰response
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // todo 6.调用成功，接口调用次数+1
                                log.info("次数+1");
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                // 7.响应日志
                                log.info("响应数据: " + data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // todo 8.调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 拒绝访问
     *
     * @param response response
     * @return Mono<Void>
     */
    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 调用失败
     *
     * @param response response
     * @return Mono<Void>
     */
    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}