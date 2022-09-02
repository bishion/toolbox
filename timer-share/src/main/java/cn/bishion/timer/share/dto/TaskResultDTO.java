package cn.bishion.timer.share.dto;

import cn.bishion.timer.share.consts.TimerShareConst;
import cn.bishion.toolkit.common.util.EnvUtil;
import cn.bishion.toolkit.common.util.ToString;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TaskResultDTO implements Serializable {

    private String host;
    private String status;
    private String msg;

    public static final TaskResultDTO success(){
        TaskResultDTO result = new TaskResultDTO();

        result.host = EnvUtil.HOST_IP;
        result.msg = "执行成功";
        result.status = TimerShareConst.STATUS_SUCCESS;
        return result;
    }
    public static final TaskResultDTO processing(){
        TaskResultDTO result = new TaskResultDTO();

        result.host = EnvUtil.HOST_IP;
        result.msg = "执行中";
        result.status = TimerShareConst.STATUS_ING;
        return result;
    }
    public static final TaskResultDTO failure(String msg){
        TaskResultDTO result = new TaskResultDTO();

        result.host = EnvUtil.HOST_IP;
        result.msg = "执行失败:"+ msg;
        result.status = TimerShareConst.STATUS_SUCCESS;
        return result;
    }


    @Override
    public String toString() {
        return ToString.toString(this);
    }


}
