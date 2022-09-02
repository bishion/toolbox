package cn.bishion.timer.share;

import cn.bishion.timer.share.dto.TaskResultDTO;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.dto.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractTaskExecutor<T, C> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public final TaskResultDTO execute(String param) {
        C cond = buildCond(param);
        int total = count(cond);
        int pageSize = getPageSize();
        int pages = total / pageSize + 1;

        try {
            for (int i = pages; i > 0; i--) {
                dealOnePage(cond, i, pageSize);
            }
            afterTaskCompleted(param);
            return TaskResultDTO.success();
        } catch (Exception e) {
            return TaskResultDTO.failure(e.getMessage());
        }
    }

    public final void dealOnePage(C cond, int current, int pageSize){
        selectOnePage(cond, current, pageSize).stream().forEach(item -> {
            try {
                execute(item);
            } catch (Exception e) {
                if (stopWhenItemError()) {
                    throw e;
                }
                if (e instanceof BizException){
                    BizException newExp = ((BizException) e);
                    log.warn("数据处理失败. data:{},code:{},msg:{}", item, newExp.getCode(),newExp.getMsg());
                }else {
                    log.error("数据处理失败. data:{}", item, e);
                }
            }
        });
    }
    protected void afterTaskCompleted(String param){
        // 默认什么都不执行
    }

    protected abstract C buildCond(String param);


    protected boolean stopWhenItemError() {
        return false;
    }

    protected abstract int count(C param);

    protected abstract void execute(T item);

    protected  int getPageSize(){
        return BaseConst.INT_50;
    }

    protected abstract List<T> selectOnePage(C cond, Integer current, Integer pageSize);
}
