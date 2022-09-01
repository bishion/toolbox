package cn.bishion.timer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.bishion.timer.mapper")
public class TimerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimerApplication.class, args);
    }
}
