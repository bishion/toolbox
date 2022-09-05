package cn.bishion.timer.web;

import cn.bishion.timer.consts.TimerConst;
import cn.bishion.timer.service.TaskCallbackService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class TaskController {

    @Resource
    private TaskCallbackService taskCallbackService;

    @PostMapping("/callback/{record}")
    public void callback(@PathVariable("record") Long recordId, @RequestBody Map<String, String> resultMsg) {
        taskCallbackService.callBack(recordId,
                resultMsg.get(TimerConst.RTN_PARAM_STATUS),
                resultMsg.get(TimerConst.RTN_PARAM_MSG),
                resultMsg.get(TimerConst.RTN_PARAM_HOST));
    }


}
