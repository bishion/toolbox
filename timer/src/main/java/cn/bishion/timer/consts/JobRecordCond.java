package cn.bishion.timer.consts;

import cn.bishion.toolkit.common.dto.PageCond;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRecordCond extends PageCond {
    private String startTimeBegin;
    private String startTimeEnd;

    private String status;
}
