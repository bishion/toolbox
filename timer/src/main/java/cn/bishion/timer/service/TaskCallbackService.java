package cn.bishion.timer.service;

import cn.bishion.timer.entity.TimerJobRecord;
import cn.bishion.timer.mapper.TimerJobRecordMapper;
import cn.bishion.timer.task.TipsMsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TaskCallbackService {
    @Resource
    private TimerJobRecordMapper timerJobRecordMapper;

    @Resource
    private TipsMsgService tipsMsgService;

    public void callBack(Long recordId, String status, String msg, String execHost) {
        TimerJobRecord jobRecord = new TimerJobRecord();

        jobRecord.setId(recordId);
        jobRecord.setStatus(status);
        jobRecord.setResultMsg(msg);
        jobRecord.setExecHost(execHost);
        jobRecord.setEndTime(new Date());

        timerJobRecordMapper.updateById(jobRecord);
        tipsMsgService.sendTipMessage(jobRecord);
    }
}
