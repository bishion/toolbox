package cn.bishion.timer.share.configuration;

import cn.bishion.timer.share.AbstractTaskExecutor;
import cn.bishion.timer.share.TaskEntryService;
import cn.bishion.timer.share.TaskOpenController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Import({TaskOpenController.class})
public class TimerShareConfiguration {
    @Bean
    public TaskEntryService taskEntryService(Map<String, AbstractTaskExecutor> taskExecutorMap,
                                             RestTemplate restTemplate){
        return new TaskEntryService(taskExecutorMap,restTemplate);
    }
}
