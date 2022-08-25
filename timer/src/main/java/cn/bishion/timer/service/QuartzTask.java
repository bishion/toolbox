package cn.bishion.timer.service;

import cn.bishion.toolkit.common.util.ToString;
import cn.hutool.core.date.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class QuartzTask implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println(DateUtil.now()+ToString.toString(context.getJobDetail().getJobDataMap()));
    }
}
