package cn.bishion.timer.task;

import cn.bishion.toolkit.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

@Slf4j
public class QuartzTask implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        TaskService taskService = SpringUtil.getBean(TaskService.TASK_BEAN_NAME, TaskService.class);
        JobKey key = context.getJobDetail().getKey();
        try{
            taskService.executeByJobId(key.getName());
        }catch (Exception e){
            log.error("任务执行失败.jobId:{}, group:{}", key.getName(), key.getGroup(),e);
        }
    }
}
