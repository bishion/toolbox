package cn.bishion.pier.demo.v1;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.EnumUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class DataExportController {

    //@Resource
    private Map<String, ExportService> exportServiceMap;


    @GetMapping("/export/{exportType}")
    public void export(@RequestBody Map<String,Object> param, @PathVariable("exportType") String exportType,
                       HttpServletResponse response){
        ExportTypeEnum exportTypeEnum = EnumUtil.fromString(ExportTypeEnum.class, exportType);
        ExportService exportService = exportServiceMap.get(exportTypeEnum.getServiceName());

        Long count = exportService.count(param);

        try (ServletOutputStream out = response.getOutputStream()) {
            //设置字符集为utf-8
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //通知浏览器服务器发送的数据格式
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode(exportTypeEnum.getFileName(), "UTF-8"));

            ExcelWriter excelWriter = EasyExcel.write(out, exportTypeEnum.getClazz()).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet").build();
            if (count > 0){
                for (int i = 0;i < count; i=i+500){
                    List records = exportService.selectByPage(param,i,500);
                    excelWriter.write(records, writeSheet);
                    records.clear();
                }
            }
            // 千万别忘记finish 会帮忙关闭流
            excelWriter.finish();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }


    }

}
