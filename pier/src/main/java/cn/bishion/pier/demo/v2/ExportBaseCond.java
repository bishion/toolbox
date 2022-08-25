package cn.bishion.pier.demo.v2;

import lombok.Data;

@Data
public class ExportBaseCond {
    private Boolean exportFlag;

    private Integer currentPage;
    private Integer pageSize;
}
