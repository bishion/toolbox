package cn.bishion.timer.service;

import cn.bishion.timer.consts.TimerConst;
import cn.bishion.timer.consts.TimerError;
import cn.bishion.timer.dto.QuartzJobDTO;
import cn.bishion.timer.entity.TimerJobConfig;
import cn.bishion.timer.mapper.QrtzCronTriggersMapperExt;
import cn.bishion.timer.mapper.TimerJobConfigMapper;
import cn.bishion.timer.dto.JobDetailDTO;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.consts.YesNoEnum;
import cn.bishion.toolkit.common.util.BaseAssert;
import cn.bishion.toolkit.common.util.IdUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class TimerAdminService {
    @Resource
    private QuartzService quartzService;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private TimerJobConfigMapper timerJobConfigMapper;

    @Resource
    private QrtzCronTriggersMapperExt qrtzCronTriggersMapperExt;

    public void addJob(JobDetailDTO jobDetailDTO) {
        // 保存
        TimerJobConfig jobDetail = BeanUtil.copyProperties(jobDetailDTO, TimerJobConfig.class);
        jobDetail.setCode(IdUtil.nextStr());
        transactionTemplate.execute(status -> {
            try {
                timerJobConfigMapper.insert(jobDetail);
                quartzService.addTask(convert2JobDTO(jobDetailDTO, jobDetail.getId()));

            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        });
    }

    public void updateJob(JobDetailDTO jobDetailDTO) {
        TimerJobConfig jobDetail = BaseAssert.getNonNull(timerJobConfigMapper.selectById(jobDetailDTO.getId()),
                TimerError.NO_DATA_BY_ID, jobDetailDTO.getId());

        transactionTemplate.execute(status -> {
            try {
                boolean cronModifyFlag = false;
                if (!StrUtil.equals(jobDetailDTO.getCron(), jobDetail.getCron())) {
                    qrtzCronTriggersMapperExt.updateCron(jobDetailDTO.getCron(), jobDetailDTO.getId().toString());
                    cronModifyFlag = true;
                }

                timerJobConfigMapper.updateById(BeanUtil.copyProperties(jobDetailDTO, TimerJobConfig.class));

                // 如果执行中改为未执行
                if (jobDetailDTO.getRunStatus() == YesNoEnum.NO && YesNoEnum.YES.name().equals(jobDetail.getRunStatus())) {
                    quartzService.stopTask(jobDetail.getId().toString(), jobDetail.getAppCode());
                }
                // 如果未执行改为执行中
                if (jobDetailDTO.getRunStatus() == YesNoEnum.YES
                        && (YesNoEnum.NO.name().equals(jobDetail.getRunStatus()) || cronModifyFlag)) {
                    quartzService.restartTask(jobDetail.getId().toString(), jobDetail.getAppCode(), jobDetailDTO.getCron());
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }

            return null;
        });

    }

    public void stopTask(String jobId, String appCode) {
        TimerJobConfig timerJobConfig = timerJobConfigMapper.selectById(jobId);

        // 更新运行状态
        transactionTemplate.execute(status -> {
            try {
                if (YesNoEnum.YES.name().equals(timerJobConfig.getRunStatus())) {
                    TimerJobConfig newJobConfig = new TimerJobConfig();
                    newJobConfig.setId(Long.valueOf(jobId));

                    newJobConfig.setRunStatus(YesNoEnum.NO.name());

                    timerJobConfigMapper.updateById(newJobConfig);
                }

                quartzService.stopTask(jobId, appCode);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        });
    }

    public void restartTask(String jobId, String appCode) {
        TimerJobConfig timerJobConfig = timerJobConfigMapper.selectById(jobId);

        transactionTemplate.execute(status -> {
            try {
                if (YesNoEnum.NO.name().equals(timerJobConfig.getRunStatus())) {
                    TimerJobConfig newJobConfig = new TimerJobConfig();
                    newJobConfig.setId(Long.valueOf(jobId));

                    newJobConfig.setRunStatus(YesNoEnum.YES.name());

                    timerJobConfigMapper.updateById(newJobConfig);
                }

                quartzService.restartTask(jobId, appCode, timerJobConfig.getCron());
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        });
    }

    public void deleteTask(String jobId, String appCode){

        transactionTemplate.execute(status -> {
            try {
                timerJobConfigMapper.deleteById(jobId);

                quartzService.deleteTask(jobId, appCode);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        });
    }
    private static QuartzJobDTO convert2JobDTO(JobDetailDTO jobDetailDTO, Long id) {
        String name = id.toString();
        QuartzJobDTO jobDTO = new QuartzJobDTO();
        jobDTO.setCron(jobDetailDTO.getCron());
        jobDTO.setGroup(jobDetailDTO.getAppCode());
        jobDTO.setJobName(name);
        jobDTO.setTriggerName(name);
        return jobDTO;
    }
}
