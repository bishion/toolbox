package cn.bishion.timer.dto;

import cn.bishion.toolkit.common.util.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QuartzJobDTO {
    private String cron;
    private String jobName;
    private String group;
    private String triggerName;

    @Override
    public String toString() {
        return ToString.toString(this);
    }

}
