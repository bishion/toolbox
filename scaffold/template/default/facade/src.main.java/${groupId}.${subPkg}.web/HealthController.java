package ${groupId}.${subPkg}.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: scaffold
 * @date: ${initDate}
 * @version: 1.0.0
 */
@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "SUCCESS";
    }
}