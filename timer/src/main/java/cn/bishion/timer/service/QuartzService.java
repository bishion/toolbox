package cn.bishion.timer.service;

import cn.bishion.timer.consts.TimerError;
import cn.bishion.timer.dto.QuartzJobDTO;
import cn.bishion.timer.task.QuartzTask;
import cn.bishion.toolkit.common.dto.BizException;
import cn.bishion.toolkit.common.util.BaseAssert;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class QuartzService {
    @Resource
    private Scheduler scheduler;

    public void addTask(QuartzJobDTO quartzJob) {
        try{
            JobDetail jobDetail = JobBuilder.newJob().
                    withIdentity(quartzJob.getJobName(), quartzJob.getGroup()).ofType(QuartzTask.class).build();

            jobDetail.getJobDataMap().putAll(quartzJob.getParam());

            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getTriggerName(),quartzJob.getGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCron())).build();
            scheduler.scheduleJob(jobDetail, trigger);

        }catch (SchedulerException e){
            log.warn("任务添加失败. jobId:{},appCode:{}",quartzJob.getJobName(),quartzJob.getGroup(),e);
            throw BizException.throwExp(TimerError.QRTZ_OPT_FAIL, "新增", quartzJob.getJobName());
        }

    }

    public void deleteTask(String jobName, String group){
        try {
            if(scheduler.getTriggerState(TriggerKey.triggerKey(jobName,group)) == Trigger.TriggerState.NONE){
                return;
            }
            BaseAssert.isTrue(scheduler.deleteJob(JobKey.jobKey(jobName,group)),TimerError.QRTZ_OPT_FAIL, "删除", jobName);
        } catch (SchedulerException e) {
            log.warn("任务删除失败. jobId:{},appCode:{}",jobName,group,e);
            throw BizException.throwExp(TimerError.QRTZ_OPT_FAIL, "删除", jobName);
        }
    }

    public void stopTask(String jobName, String group){
        try {
            if(scheduler.getTriggerState(TriggerKey.triggerKey(jobName,group)) == Trigger.TriggerState.PAUSED){
                return;
            }
            scheduler.pauseJob(JobKey.jobKey(jobName, group));
        } catch (SchedulerException e) {
            log.warn("任务暂停失败. jobId:{},appCode:{}",jobName,group,e);
            throw BizException.throwExp(TimerError.QRTZ_OPT_FAIL, "新增", jobName);
        }
    }
    public void restartTask(String jobName, String group, String cron){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, group);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, group)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.rescheduleJob(triggerKey,trigger);
        } catch (SchedulerException e) {
            log.warn("任务重启失败. jobId:{},appCode:{}",jobName,group,e);
            throw BizException.throwExp(TimerError.QRTZ_OPT_FAIL, "启动", jobName);
        }
    }
}
