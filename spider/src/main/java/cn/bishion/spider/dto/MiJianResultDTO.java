package cn.bishion.spider.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MiJianResultDTO {
    @ExcelProperty(value = "标题",index = 0)
    private String title;
    @ExcelProperty(value = "作者",index = 1)
    private String author;
    @ExcelProperty(value = "写入时间",index = 2)
    private String writeDate;
    @ExcelProperty(value = "内容",index = 3)
    private String content;
    @ExcelProperty(value = "评论",index = 4)
    private String comment;
    @ExcelProperty(value = "路径",index = 5)
    private String url;
}
