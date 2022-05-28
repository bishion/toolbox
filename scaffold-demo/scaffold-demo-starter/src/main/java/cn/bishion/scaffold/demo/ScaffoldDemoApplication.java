package cn.bishion.scaffold.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 脚手架演示应用程序
 *
 * @author: guofangbi
 * @date: 2022-05-27 09:06:40
 * @version: 1.0.0
 */
@SpringBootApplication
@MapperScan("cn.bishion.scaffold.demo.service.**.mapper")
@EnableFeignClients({"cn.bishion.scaffold.demo.service.**.south"})
public class ScaffoldDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScaffoldDemoApplication.class, args);
    }
}
