package cn.bishion.timer.mapper;

import cn.bishion.timer.entity.QrtzCronTriggers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface QrtzCronTriggersMapperExt extends BaseMapper<QrtzCronTriggers> {

    @Update("update qrtz_cron_triggers set cron_expression=#{cron}  where trigger_name=#{triggerName}")
    void updateCron(@Param("cron") String cron,@Param("triggerName") String triggerName);
}
