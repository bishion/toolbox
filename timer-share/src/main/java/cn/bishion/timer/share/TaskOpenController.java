package cn.bishion.timer.share;

import cn.bishion.timer.share.dto.TaskResultDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class TaskOpenController {
    @Resource
    private TaskEntryService taskEntryService;
    @PostMapping("/task/entry")
    public TaskResultDTO task(@RequestBody Map<String,String> param){
        return taskEntryService.execute(param);
    }

}
