package cn.bishion.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoController {
    @Resource
    private BingEngine bingEngine;
    @RequestMapping("/search")
    public String callBing(){
        return bingEngine.search("呵呵哒");
    }
}
