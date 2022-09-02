package cn.bishion.timer.config;

import cn.bishion.toolkit.common.consts.BaseConst;
import cn.hutool.core.thread.ThreadUtil;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@Configuration
public class TimerConfiguration {
    @Bean
    public RestTemplate taskRestTemplate(){
        ConnectionPool connectionPool = new ConnectionPool(BaseConst.INT_50, BaseConst.INT_5, TimeUnit.MINUTES);
        OkHttpClient httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(false).connectionPool(connectionPool).build();
        ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    @Bean
    public ExecutorService saveRecordExecutor(){
        return new ThreadPoolExecutor(BaseConst.INT_1,BaseConst.INT_1, BaseConst.INT_1, TimeUnit.DAYS,
                new LinkedBlockingQueue<>(BaseConst.INT_100), Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.CallerRunsPolicy());

    }

}
