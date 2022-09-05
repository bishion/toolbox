package cn.bishion.timer.web;

import cn.bishion.timer.consts.JobRecordCond;
import cn.bishion.timer.consts.RecordStatusEnum;
import cn.bishion.timer.consts.TimerError;
import cn.bishion.timer.dto.JobRecordDTO;
import cn.bishion.timer.entity.TimerJobRecord;
import cn.bishion.timer.mapper.TimerJobRecordMapper;
import cn.bishion.toolkit.common.dto.BaseResult;
import cn.bishion.toolkit.common.dto.PageResult;
import cn.bishion.toolkit.common.util.BaseAssert;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/record")
public class RecordAdminController {

    @Resource
    TimerJobRecordMapper timerJobRecordMapper;

    @PostMapping("/close/{id}")
    public BaseResult<Void> closeRecord(@PathVariable("id") Long id, String msg) {
        TimerJobRecord dbRecord = timerJobRecordMapper.selectById(id);

        // 非ING的record 不允许关闭
        BaseAssert.isTrue(StrUtil.equals(dbRecord.getStatus(), RecordStatusEnum.ING.name()), TimerError.QRTZ_OPT_FAIL,
                dbRecord.getResultMsg());

        TimerJobRecord jobRecord = new TimerJobRecord();
        jobRecord.setId(id);
        jobRecord.setStatus(RecordStatusEnum.CLOSE.name());
        jobRecord.setEndTime(new Date());
        jobRecord.setResultMsg(RecordStatusEnum.CLOSE.getDesc() + msg);

        timerJobRecordMapper.updateById(jobRecord);
        return BaseResult.success();
    }

    @GetMapping("/query/{jobId}")
    public BaseResult<PageResult<JobRecordDTO>> queryRecordList(@PathVariable("jobId") Long jobId, JobRecordCond cond) {
        LambdaQueryWrapper<TimerJobRecord> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(TimerJobRecord::getJobId, jobId);
        if (StrUtil.isNotBlank(cond.getStartTimeBegin())) {
            Date startTime = DateUtil.parseDate(cond.getStartTimeBegin());
            wrapper.gt(TimerJobRecord::getStartTime, startTime);
        }
        if (StrUtil.isNotBlank(cond.getStartTimeEnd())) {
            Date endTime = DateUtil.parseDate(cond.getStartTimeEnd());
            wrapper.gt(TimerJobRecord::getStartTime, endTime);
        }

        wrapper.eq(StrUtil.isNotBlank(cond.getStatus()), TimerJobRecord::getStatus, cond.getStartTimeBegin());

        Page<TimerJobRecord> pageResult = timerJobRecordMapper
                .selectPage(new Page<>(cond.getCurrentPage(), cond.getPageSize()), wrapper);


        return BaseResult.success(PageResult.of(pageResult.getTotal(), pageResult.getRecords()
                .parallelStream().map(item -> BeanUtil.copyProperties(item, JobRecordDTO.class))
                .collect(Collectors.toList())));
    }
    
}
