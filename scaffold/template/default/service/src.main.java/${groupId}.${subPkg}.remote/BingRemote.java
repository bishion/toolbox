package ${groupId}.${subPkg}.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Bishion
 * @date: 2022/5/28-8:01
 * @version: 1.0.0
 */
@FeignClient("https://cn.bing.com")
public interface BingRemote {
    /**
     * 搜索
     *
     * @param keyword 关键字
     * @return {@link String}
     */
    @RequestMapping("/search")
    String search(@RequestParam("q") String keyword);
}
