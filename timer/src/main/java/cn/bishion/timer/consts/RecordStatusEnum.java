package cn.bishion.timer.consts;

public enum RecordStatusEnum {
    ING("执行中"),
    SUCCESS("执行成功"),
    FAILURE("执行失败"),
    ERROR("执行出错"),
    ;

    private String desc;

    RecordStatusEnum(String desc) {
        this.desc = desc;
    }
}
