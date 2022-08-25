package cn.bishion.pier.demo.v2;

public @interface ExcelExport {
    // 导出到Excel的实体
    Class targetType();

    // 每页大小
    int pageSize() default 5000;

    // 同步下载最大条数
    int syncMaxSize() default 50000;

    // 文件名
    String title();

}
