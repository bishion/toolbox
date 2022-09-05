package cn.bishion.timer.consts;

import cn.bishion.toolkit.common.consts.BaseError;

public enum TimerError implements BaseError {
    INVALID_CRON("10101", "错误的 cron 表达式:{0}"),
    NO_DATA_BY_ID("10102", "根据ID找不到任务配置:{0}"),
    QRTZ_OPT_FAIL("10103", "调度器{0}失败,id:{1}"),
    QRTZ_QRY_FAIL("10104", "查询正在运行的任务失败.{0}"),
    RCD_NOT_ING("10105", "该执行记录非运行中：{0}"),
    ;

    TimerError(String code, String pattern) {
        this.code = code;
        this.pattern = pattern;
    }

    private final String code;
    private final String pattern;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
