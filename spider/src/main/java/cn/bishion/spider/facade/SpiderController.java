package cn.bishion.spider.facade;

import cn.bishion.spider.consts.SiteType;
import cn.bishion.spider.service.SpiderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: guofangbi
 * @date: 2022/6/28-9:21
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {
    @Resource
    private SpiderService spiderService;
    @GetMapping("/miJian")
    public void grabMiJian(){
        spiderService.spiderByEnterUrl(SiteType.MI_JIAN);
    }
    @GetMapping("/miJian/{startId}/{endId}")
    public void grabMiJian(@PathVariable("startId")int startId,@PathVariable("endId") int endId){
        spiderService.spiderMiJianPageById(SiteType.MI_JIAN, startId, endId);
    }

}
