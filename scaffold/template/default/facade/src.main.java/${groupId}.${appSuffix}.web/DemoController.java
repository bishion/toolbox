package ${groupId}.${appSuffix}.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: scaffold
 * @date: ${initDate}
 * @version: 1.0.0
 */
@RestController
public class DemoController {
    @GetMapping("/health")
    public String health() {
        return "SUCCESS";
    }
}