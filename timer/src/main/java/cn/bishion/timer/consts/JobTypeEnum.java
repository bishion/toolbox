package cn.bishion.timer.consts;

import cn.bishion.timer.task.QuartzTask;
import cn.bishion.timer.task.TaskService;

public enum JobTypeEnum {
    SYNC("同步执行"),
    ASYNC("异步执行");
    private String desc;

    JobTypeEnum(String desc){
        this.desc = desc;
    }
}
