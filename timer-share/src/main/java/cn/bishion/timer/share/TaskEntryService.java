package cn.bishion.timer.share;

import cn.bishion.timer.share.consts.TimerShareConst;
import cn.bishion.timer.share.dto.TaskResultDTO;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.dto.BaseReqInfo;
import cn.bishion.toolkit.common.dto.ReqInfoHolder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TaskEntryService {

    private static final Logger log = LoggerFactory.getLogger(TaskEntryService.class);

    private static final String application = "application/json; charset=utf-8";
    private Map<String, AbstractTaskExecutor> taskExecutorMap;
    private RestTemplate restTemplate;

    public TaskEntryService(Map<String, AbstractTaskExecutor> taskExecutorMap, RestTemplate restTemplate) {
        this.taskExecutorMap = taskExecutorMap;
        this.restTemplate = restTemplate;
    }

    private OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(BaseConst.INT_50, TimeUnit.SECONDS)
            .writeTimeout(BaseConst.INT_50, TimeUnit.SECONDS)
            .readTimeout(BaseConst.INT_50, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    public TaskResultDTO execute(Map<String, String> paramMap) {
        String beanName = paramMap.get(TimerShareConst.REQ_FIELD_BEAN);
        String callbackUrl = paramMap.get(TimerShareConst.REQ_FIELD_CALLBACK);
        String param = paramMap.get(TimerShareConst.REQ_FIELD_PARAM);

        AbstractTaskExecutor taskExecutor = taskExecutorMap.get(beanName);
        if (Objects.isNull(taskExecutor)) {
            return TaskResultDTO.failure("未找到对应的服务:" + beanName);
        }
        return StrUtil.isBlank(callbackUrl) ? taskExecutor.execute(param) : this.execAsync(callbackUrl, taskExecutor, param);

    }

    private TaskResultDTO execAsync(String callbackUrl, AbstractTaskExecutor taskExecutor, String param) {
        BaseReqInfo reqInfoHolder = ReqInfoHolder.getReqInfo();
        ThreadUtil.execute(() -> {
            ReqInfoHolder.setReqInfo(reqInfoHolder);
            TaskResultDTO resultDTO = null;
            try {
                resultDTO = taskExecutor.execute(param);
            } catch (Exception e) {
                resultDTO = TaskResultDTO.failure(e.getMessage());
            } finally {
                ReqInfoHolder.removeOptInfo();
                try {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    HttpEntity<String> request = new HttpEntity<>(JSONUtil.toJsonStr(resultDTO), httpHeaders);
                    restTemplate.postForObject(callbackUrl, request, TaskResultDTO.class);
                } catch (Exception e) {
                    log.error("回调失败. url:{},data:{}", callbackUrl, resultDTO, e);
                }
            }


        });

        return TaskResultDTO.processing();

    }


}
