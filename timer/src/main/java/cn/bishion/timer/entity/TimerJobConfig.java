package cn.bishion.timer.entity;

import cn.bishion.toolkit.common.util.ToString;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("timer_job_config")
public class TimerJobConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Long   id;
    private String code;
    private String appCode;
    private String name;
    private String cron;
    private String note;
    private String param;
    private String warnMails;
    private String url;
    private String runStatus;

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
