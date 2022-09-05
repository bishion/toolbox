package cn.bishion.timer.consts;

public enum RecordStatusEnum {
    ING("执行中。。。"),
    SUCCESS("执行成功。"),
    FAILURE("执行失败："),
    ERROR("调度异常："),
    CLOSE("手动关闭："),
    ;

    private final String desc;

    RecordStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
