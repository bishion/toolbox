package cn.bishion.timer.consts;

import cn.bishion.toolkit.common.consts.BaseError;

public enum TimerError implements BaseError {
    INVALID_CRON("10101","错误的 cron 表达式:{0}"),
    NO_DATA_BY_ID("10102", "根据ID找不到任务配置:{0}"),
    QRTZ_OPT_FAIL("10103", "调度器{0}失败,id:{1}"),
    ;
    TimerError(String code, String pattern){
        this.code = code;
        this.pattern = pattern;
    }
    private String code;
    private String pattern;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
