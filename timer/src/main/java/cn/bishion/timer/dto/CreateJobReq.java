package cn.bishion.timer.dto;

import cn.bishion.toolkit.common.util.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobReq {
    private String jobName;
    private String cron;
    private String param;

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
