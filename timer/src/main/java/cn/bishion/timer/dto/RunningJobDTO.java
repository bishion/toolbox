package cn.bishion.timer.dto;

import cn.bishion.toolkit.common.consts.YesNoEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RunningJobDTO implements Serializable {

    private Long id;
    private String name;
    private String appCode;

    private String memStatus;

    private String dbStatus;

    public RunningJobDTO() {

    }

    public RunningJobDTO(String id, String appCode) {
        this.id = Long.parseLong(id);
        this.appCode = appCode;
        this.memStatus = YesNoEnum.YES.name();

    }

    public RunningJobDTO(Long id, String name, String appCode) {
        this.id = id;
        this.appCode = appCode;
        this.name = name;
        this.dbStatus = YesNoEnum.YES.name();

    }
}
