package cn.bishion.timer.web;

import cn.bishion.timer.dto.CreateJobReq;
import cn.bishion.timer.service.QuartzService;
import cn.bishion.toolkit.common.dto.BaseResult;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/quartz")
public class QuartzAdminController {

    @Resource
    private QuartzService quartzService;
    @PostMapping("/add")
    public BaseResult<Void> addJob(@RequestBody CreateJobReq jobReq) throws SchedulerException {

        quartzService.addJob(jobReq);
        return BaseResult.success();
    }

    @PostMapping("/pause")
    public BaseResult<Void> pauseJob(){
        quartzService.pauseJob("");
        return BaseResult.success();
    }
    @PostMapping("/resume")
    public BaseResult<Void> resumeJob(){
        quartzService.resumeJob("");
        return BaseResult.success();
    }
    @PostMapping("/delete")
    public BaseResult<Void> deleteJob(){
        quartzService.deleteJob("");
        return BaseResult.success();
    }
}
