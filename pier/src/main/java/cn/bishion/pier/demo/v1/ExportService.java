package cn.bishion.pier.demo.v1;

import java.util.List;
import java.util.Map;

public interface ExportService {

    /**
     * 根据条件查询出数据总条数
     * @param cond
     * @return
     */
    Long count(Map cond);
    /**
     * 根据条件和起始记录，查询一页条数
     * @param cond
     * @return
     */
    List selectByPage(Map cond, Integer start, Integer pageSize);


}
