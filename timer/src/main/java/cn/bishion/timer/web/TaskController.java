package cn.bishion.timer.web;

import cn.bishion.timer.consts.TimerConst;
import cn.bishion.timer.service.QuartzService;
import cn.bishion.timer.service.TaskCallbackService;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
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
    @PostMapping("/trigger")
    public String trigger(@RequestBody Map<String,String> param){
        return JSONUtil.toJsonStr(param);
    }
    @PostMapping("/task")
    public Map<String,String > task(@RequestBody Map<String,String> param){
        System.out.println(JSONUtil.toJsonStr(param));
        Map<String, String> map = Maps.newHashMap();

        if (param.containsKey(TimerConst.REQ_PARAM_CALLBACK)){
            String url = param.get(TimerConst.REQ_PARAM_CALLBACK);
            map.put("status", "ING");
            map.put("msg", "heheda");
            map.put("host", "127.0.0.1");

            ThreadUtil.execAsync(() -> {
                ThreadUtil.sleep(10000L);
                taskCallbackService.callBack("127.0.1.1","SUCCESS",
                        url.substring(url.lastIndexOf("/")+1),"heheda");
            });

        }else{
            map.put("status", "FAILURE");
            map.put("msg", "heheda");
            map.put("host", "127.0.0.1");
        }

        return map;
    }

    @PostMapping("/callback/{code}")
    public void callback(@PathVariable("code") String code,@RequestBody Map<String,String> resultMsg) {
        taskCallbackService.callBack(code,
                resultMsg.get(TimerConst.RTN_PARAM_STATUS),
                resultMsg.get(TimerConst.RTN_PARAM_MSG),
                resultMsg.get(TimerConst.RTN_PARAM_HOST));
    }
}
