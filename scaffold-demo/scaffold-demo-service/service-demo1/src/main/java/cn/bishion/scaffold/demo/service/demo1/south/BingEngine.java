package cn.bishion.scaffold.demo.service.demo1.south;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: guofangbi
 * @date: 2022/5/28-8:01
 * @version: 1.0.0
 */
@FeignClient("http://www.baidu.com")
public interface BingEngine {
    /**
     * 搜索
     *
     * @param keyword 关键字
     * @return {@link String}
     */
    @RequestMapping("/search")
    String search(@RequestParam("q") String keyword);
}
