package cn.bishion.scaffold.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Bishion
 * @date: 2022/6/23-8:29
 * @version: 1.0.0
 */
@RestController
public class DemoController {
    @GetMapping("/health")
    public String health() {
        return "SUCCESS";
    }
}
