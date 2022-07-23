package cn.bishion.spider.service;

import cn.bishion.spider.dto.MiJianResultDTO;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class MiJianSaveService {


    private static final String EXCEL_FILE = "/alidata1/admin/spider/";
    ExcelWriter writer ;
    WriteSheet writeSheet ;

    public void open(){
    writer = EasyExcel.write(EXCEL_FILE+ DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN)+".xls",
            MiJianResultDTO.class).build();
    writeSheet = EasyExcel.writerSheet().build();

}

    public void saveContent(MiJianResultDTO miJianResult){
        writer.write(Arrays.asList(miJianResult), writeSheet);
    }

    public void close(){
        System.out.println("解析结束");
        writer.finish();

    }
}
