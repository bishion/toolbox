package cn.bishion.timer.task;

import cn.bishion.timer.entity.TimerJobConfig;
import cn.bishion.timer.entity.TimerJobRecord;
import org.springframework.stereotype.Service;

@Service
public class TipsMsgService {
    public void sendTipMessage(TimerJobConfig jobConfig, TimerJobRecord jobRecord){
        // 发送提示信息
        // 这里可以根据record的状态来判断是否要发送信息
    }
    public void sendTipMessage(TimerJobRecord jobRecord){
        // 发送提示信息
        // 这里可以根据record的状态来判断是否要发送信息
    }
}
