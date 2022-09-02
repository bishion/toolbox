package cn.bishion.timer.task;

import cn.bishion.timer.consts.JobTypeEnum;
import cn.bishion.timer.consts.RecordStatusEnum;
import cn.bishion.timer.consts.TimerConst;
import cn.bishion.timer.entity.TimerJobConfig;
import cn.bishion.timer.entity.TimerJobRecord;
import cn.bishion.timer.mapper.TimerJobConfigMapper;
import cn.bishion.timer.mapper.TimerJobRecordMapper;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service(TaskService.TASK_BEAN_NAME)
public class TaskService {
    public static final String TASK_BEAN_NAME = "taskService";

    @Value("${timer.callbackUrl:http://localhost:8080/callback/}")
    private String callbackUrl;
    @Resource
    private RestTemplate taskRestTemplate;
    @Resource
    private TimerJobConfigMapper timerJobConfigMapper;
    @Resource
    private TimerJobRecordMapper timerJobRecordMapper;

    @Resource
    private ExecutorService saveRecordExecutor;

    public void executeByJobId(String jobId) {
        TimerJobConfig jobConfig = timerJobConfigMapper.selectById(jobId);
        if (Objects.isNull(jobConfig)) {
            log.error("根据 id 查不到任务配置:{}", jobId);
            return;
        }

        if (jobIsRunning(jobId)) {
            return;
        }
        String recordCode = IdUtil.nextStr();
        TimerJobRecord jobRecord = initJobRecord(jobConfig, recordCode);

        HttpEntity<String> request = buildRequest(jobConfig, recordCode);
        try {
            Map<String, String> map = taskRestTemplate.postForObject(jobConfig.getUrl(), request, Map.class);
            assembleTaskResult(jobRecord, map);
        } catch (Exception e) {
            jobRecord.setStatus(RecordStatusEnum.ERROR.name());
            jobRecord.setResultMsg(e.getMessage());
            log.error("任务执行失败. jobInfo:{}", jobConfig, e);
        } finally {
            jobRecord.setEndTime(new Date());
            saveRecordExecutor.execute(() -> timerJobRecordMapper.updateById(jobRecord));
        }
    }

    private boolean jobIsRunning(String jobId) {
        LambdaQueryWrapper<TimerJobRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TimerJobRecord::getJobId, jobId);
        queryWrapper.eq(TimerJobRecord::getStatus, RecordStatusEnum.ING.name());
        if (timerJobRecordMapper.selectCount(queryWrapper) > 0) {
            log.info("任务执行中.id:{}", jobId);
            return true;
        }
        return false;
    }

    private void assembleTaskResult(TimerJobRecord jobRecord, Map<String, String> result) {
        jobRecord.setStatus(result.get(TimerConst.RTN_PARAM_STATUS));
        jobRecord.setExecIp(result.get(TimerConst.RTN_PARAM_HOST));
        jobRecord.setResultMsg(result.get(TimerConst.RTN_PARAM_MSG));
    }


    private TimerJobRecord initJobRecord(TimerJobConfig jobConfig, String recordCode) {
        TimerJobRecord jobRecord = new TimerJobRecord();
        jobRecord.setCode(recordCode);
        jobRecord.setStartTime(new Date());
        jobRecord.setBeanName(jobConfig.getBeanName());
        jobRecord.setParam(jobConfig.getParam());
        jobRecord.setJobId(jobConfig.getId());
        jobRecord.setStatus(RecordStatusEnum.ING.name());
        timerJobRecordMapper.insert(jobRecord);
        TimerJobRecord newRecord = new TimerJobRecord();
        newRecord.setCode(recordCode);

        return newRecord;
    }

    private HttpEntity<String> buildRequest(TimerJobConfig jobConfig, String recordCode) {
        Map<String, String> param = Maps.newHashMapWithExpectedSize(BaseConst.INT_2);
        param.put(TimerConst.REQ_PARAM_BEAN, jobConfig.getBeanName());
        param.put(TimerConst.REQ_PARAM_KEY, jobConfig.getParam());
        if (JobTypeEnum.ASYNC.name().equals(jobConfig.getJobType())) {
            param.put(TimerConst.REQ_PARAM_CALLBACK, callbackUrl + recordCode);
        }
        param.put(TimerConst.REQ_PARAM_KEY, jobConfig.getParam());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return new HttpEntity<>(JSONUtil.toJsonStr(param), httpHeaders);

    }
}
