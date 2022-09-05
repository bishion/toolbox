package cn.bishion.timer.web;

import cn.bishion.timer.consts.TimerError;
import cn.bishion.timer.dto.JobDetailDTO;
import cn.bishion.timer.dto.RunningJobDTO;
import cn.bishion.timer.service.TimerAdminService;
import cn.bishion.toolkit.common.dto.BaseResult;
import cn.bishion.toolkit.common.util.BaseAssert;
import org.quartz.CronExpression;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/task")
public class TimerAdminController {

    @Resource
    private TimerAdminService timerAdminService;

    @PostMapping("/add")
    public BaseResult<Void> addTask(@RequestBody @Valid JobDetailDTO jobReq) {
        // 校验是否正常的cron表达式
        BaseAssert.isTrue(CronExpression.isValidExpression(jobReq.getCron()), TimerError.INVALID_CRON, jobReq.getCron());
        timerAdminService.addJob(jobReq);

        return BaseResult.success();
    }

    @PostMapping("/update")
    public BaseResult<Void> updateTask(@RequestBody JobDetailDTO jobReq) {

        timerAdminService.updateJob(jobReq);
        return BaseResult.success();
    }

    @PostMapping("/stop/{appCode}/{id}")
    public BaseResult<Void> stopTask(@PathVariable("appCode") String appCode, @PathVariable("id") String id) {
        timerAdminService.stopTask(id, appCode);
        return BaseResult.success();
    }

    @PostMapping("/restart/{appCode}/{id}")
    public BaseResult<Void> restartJob(@PathVariable("appCode") String appCode, @PathVariable("id") String id) {
        timerAdminService.restartTask(id, appCode);
        return BaseResult.success();
    }

    @PostMapping("/delete/{appCode}/{id}")
    public BaseResult<Void> deleteJob(@PathVariable("appCode") String appCode, @PathVariable("id") String id) {
        timerAdminService.deleteTask(id, appCode);
        return BaseResult.success();
    }

    @GetMapping("/task/running")
    public BaseResult<List<RunningJobDTO>> queryRunningTask() {
        return BaseResult.success(timerAdminService.queryAllRunningTask());
    }
}
