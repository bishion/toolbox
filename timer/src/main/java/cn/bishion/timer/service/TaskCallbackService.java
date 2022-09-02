package cn.bishion.timer.service;

import cn.bishion.timer.entity.TimerJobRecord;
import cn.bishion.timer.mapper.TimerJobRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TaskCallbackService {
    @Resource
    private TimerJobRecordMapper timerJobRecordMapper;

    public void callBack(String code, String status, String msg, String execIp) {
        TimerJobRecord jobRecord = new TimerJobRecord();

        jobRecord.setCode(code);
        jobRecord.setStatus(status);
        jobRecord.setResultMsg(msg);
        jobRecord.setExecIp(execIp);
        jobRecord.setEndTime(new Date());

        timerJobRecordMapper.updateById(jobRecord);
    }
}
