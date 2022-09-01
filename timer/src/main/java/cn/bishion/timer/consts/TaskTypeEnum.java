package cn.bishion.timer.consts;

import cn.bishion.timer.task.AsyncTask;
import cn.bishion.timer.task.SyncTask;

public enum TaskTypeEnum {
    SYNC(SyncTask.class,"同步执行"),
    ASYNC(AsyncTask.class,"异步执行");
    private Class<?> clazz;
    private String desc;

    TaskTypeEnum(Class<?> clazz, String desc){
        this.clazz = clazz;
        this.desc = desc;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
