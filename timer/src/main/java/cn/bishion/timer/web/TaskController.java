package cn.bishion.timer.web;

import cn.bishion.timer.service.QuartzService;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class TaskController {

    @Resource
    private QuartzService quartzService;
    @PostMapping("/task")
    public String trigger(@RequestBody Map<String,String> param){
        quartzService.deleteTask("job2","GUOFANGBI");
        return JSONUtil.toJsonStr(param);
    }
}
