package cn.bishion.timer.service;

import cn.bishion.timer.consts.RecordStatusEnum;
import cn.bishion.timer.entity.TimerJobRecord;
import cn.bishion.timer.mapper.TimerJobRecordMapper;
import cn.bishion.timer.share.AbstractTaskExecutor;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobTimeoutWarnTask extends AbstractTaskExecutor<TimerJobRecord, LambdaQueryWrapper<TimerJobRecord>> {
    @Resource
    private TimerJobRecordMapper timerJobRecordMapper;

    @Override
    protected LambdaQueryWrapper<TimerJobRecord> buildCond(String param) {
        LambdaQueryWrapper<TimerJobRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimerJobRecord::getStatus, RecordStatusEnum.SUCCESS);

        return wrapper;
    }

    @Override
    protected int count(LambdaQueryWrapper<TimerJobRecord> param) {

        return timerJobRecordMapper.selectCount(param);
    }

    @Override
    protected void execute(TimerJobRecord item) {
        ThreadUtil.sleep(10L);
        //System.out.println(item.getCode());
    }

    @Override
    protected List<TimerJobRecord> selectOnePage(LambdaQueryWrapper<TimerJobRecord> cond, Integer current, Integer pageSize) {
        return timerJobRecordMapper.selectPage(new Page<>(current, pageSize), cond).getRecords();
    }
}
