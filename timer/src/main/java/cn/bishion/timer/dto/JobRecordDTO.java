package cn.bishion.timer.dto;

import cn.bishion.toolkit.common.util.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class JobRecordDTO implements Serializable {
    private Long id;
    private Long jobId;
    private String execHost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String status;
    private String resultMsg;

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
