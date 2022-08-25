package cn.bishion.timer.service;

import cn.bishion.timer.dto.CreateJobReq;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QuartzService {
    @Resource
    private Scheduler scheduler;
    public void addJob(CreateJobReq jobReq) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob().
                withIdentity("job2", "GUOFANGBI").ofType(QuartzTask.class).build();
        jobDetail.getJobDataMap().put("hehe","heheda");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "GUOFANGBI")
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?")).build();

        scheduler.scheduleJob(jobDetail, trigger);

    }
    public void deleteJob(String jobId){
        JobKey jobKey = JobKey.jobKey("job1","GUOFANGBI");
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void pauseJob(String jobId){
        try {
            scheduler.pauseJob(JobKey.jobKey("job1","GUOFANGBI"));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    public void resumeJob(String jobId){
        try {
            scheduler.resumeJob(JobKey.jobKey("job1","GUOFANGBI"));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
