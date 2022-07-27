package cn.bishion.scaffold.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Bishion
 * @date: 2022/6/21-10:55
 * @version: 1.0.0
 */
@Getter
@Setter
@ApiModel("代码生成参数")
public class GenerateRequest {
    @ApiModelProperty(value = "groupId,也会作为包名, 可不填，默认当前组织groupId")
    private String groupId;
    @ApiModelProperty(value = "应用中文名",required = true)
    private String appName;
    @ApiModelProperty(value = "应用编码,也会作为artifactId",required = true)
    private String appCode;
    @ApiModelProperty(value = "应用描述, 非必填, 默认appName")
    private String appDesc;
    @ApiModelProperty(value = "subPkg,用来拼接作为二级包名：groupId.subPkg, 非必填, 默认appCode的第二段")
    private String subPkg;
    @ApiModelProperty(value = "应用类型,可以理解为代码模板文件夹中不同的目录, 非必填,默认default")
    private String appStyle;

}
