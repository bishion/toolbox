package cn.bishion.pier.demo.v2;

import cn.bishion.toolkit.common.dto.PageResult;
import cn.hutool.core.util.BooleanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Objects;

@Slf4j
public class ExcelExportAspect {
    @Around("exportPointCut()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ExportBaseCond exportBaseCond = null;
        for (Object obj : proceedingJoinPoint.getArgs()){
            if (obj instanceof ExportBaseCond){
                exportBaseCond = ((ExportBaseCond) obj);
                break;
            }
        }
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        // 如果参数不符合要求，则直接返回
        if (Objects.isNull(exportBaseCond) || BooleanUtil.isFalse(exportBaseCond.getExportFlag()) ||
        !(method.getReturnType() == PageResult.class)){
            return proceedingJoinPoint.proceed();
        }

        Object result = proceedingJoinPoint.proceed();
        PageResult pageResult = ((PageResult<?>) result);
        ExcelExport export = method.getAnnotation(ExcelExport.class);
        // 注意：这里只考虑了实时导出的代码，异步的导出可以自行扩展
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
        //设置字符集为utf-8
        response.setCharacterEncoding("utf8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //通知浏览器服务器发送的数据格式
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(export.title(), "UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out, export.targetType()).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("sheet").build();
        Integer count = pageResult.getTotal();
        if ( count> 0){
            long pages = count/export.pageSize()+1;
            exportBaseCond.setPageSize(export.pageSize());
            for (int i = 0;i < pages; i++){
                exportBaseCond.setCurrentPage(i);
                pageResult = (PageResult) proceedingJoinPoint.proceed();
                excelWriter.write(pageResult.getRecords(), writeSheet);
            }
        }
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
        out.flush();
        return null;
    }
}
