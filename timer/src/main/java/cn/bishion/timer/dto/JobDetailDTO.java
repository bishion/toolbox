package cn.bishion.timer.dto;

import cn.bishion.timer.consts.JobTypeEnum;
import cn.bishion.toolkit.common.consts.YesNoEnum;
import cn.bishion.toolkit.common.util.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JobDetailDTO {
    @NotBlank(message="任务名称不能为空")
    private String name;
    @NotBlank(message="应用编码不能为空")
    private String appCode;
    @NotBlank(message="任务表达式不能为空")
    private String cron;
    @NotBlank(message="任务参数不能为空")
    private String param;
    private String note;
    private Long id;
    @NotBlank(message="消息接收人不能为空")
    private String tipsReceiver;
    @NotBlank(message="调度地址不能为空")
    private String url;
    @NotNull(message="是否执行不能为空")
    private YesNoEnum runStatus;
    @NotNull(message="任务类型不能为空")
    private JobTypeEnum jobType;
    @NotNull(message="任务执行器不能为空")
    private String beanName;

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
