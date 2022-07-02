package ${groupId}.${appSuffix}.service

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: scaffold
 * @date: 2022/6/23-8:29
 * @version: 1.0.0
 */
@RestController
public class DemoService {
    @GetMapping("/health")
    public String health() {
        return "SUCCESS";
    }
}