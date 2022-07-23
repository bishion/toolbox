package cn.bishion.scaffold.service;

import cn.bishion.scaffold.consts.ScaffoldError;
import cn.bishion.toolkit.common.dto.BizException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author: Bishion
 * @date: 2022/6/19-20:26
 * @version: 1.0.0
 */
@Slf4j
@Service
public class FreeMarkerService {
    private static final String BASE_PATH = "D:\\Workspace\\toolbox\\scaffold\\template\\default";

    @SneakyThrows
    public void buildFile(Object dataModel, String sourceFile, String targetFile) {

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDirectoryForTemplateLoading(new File(BASE_PATH));
        cfg.setDefaultEncoding(CharsetUtil.UTF_8);
        File file = FileUtil.file(targetFile);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), CharsetUtil.UTF_8))) {
            Template template = cfg.getTemplate(sourceFile);
            template.process(dataModel, out);
        } catch (Exception e) {
            log.error("模板解析失败.{},{}", sourceFile, targetFile, e);
            throw BizException.throwExp(ScaffoldError.FTL_PARSE_ERROR, sourceFile + e.getMessage());
        }

    }
}
