package cn.bishion.pier;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.bishion.pier.mapper")
public class PierApplication {
    public static void main(String[] args) {
        SpringApplication.run(PierApplication.class, args);
    }
}
