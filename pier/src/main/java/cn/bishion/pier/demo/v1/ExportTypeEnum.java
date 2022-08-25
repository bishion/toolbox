package cn.bishion.pier.demo.v1;

public enum ExportTypeEnum {
    NORMAL_ORDER("normalOrderExportService",NormalOrderExportDTO.class,"订单明细.xlsx"),
    ORDER_WITH_PRODUCT("orderWithProductExportService",NormalOrderExportDTO.class,"订单明细(带商品).xlsx");

    private String serviceName;
    private Class clazz;
    private String fileName;

    ExportTypeEnum(String serviceName,Class clazz,String fileName){
        this.serviceName = serviceName;
        this.clazz = clazz;
        this.fileName = fileName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getFileName() {
        return fileName;
    }
}
