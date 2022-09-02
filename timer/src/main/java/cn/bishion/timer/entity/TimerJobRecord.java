package cn.bishion.timer.entity;

import cn.bishion.toolkit.common.util.ToString;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("timer_job_record")
public class TimerJobRecord {
    @TableId(value = "code", type = IdType.ASSIGN_ID)
    private String code;
    private Long jobId;
    private String execIp;
    private String beanName;
    private String param;
    private Date startTime;
    private Date endTime;
    private String status;
    private String resultMsg;

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}