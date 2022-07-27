package cn.bishion.scaffold.service;

import cn.bishion.scaffold.consts.ScaffoldError;
import cn.bishion.toolkit.common.dto.BizException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author: Bishion
 * @date: 2022/6/19-20:26
 * @version: 1.0.0
 */
@Slf4j
@Service
public class FreeMarkerService {
    public void buildFile(Path basePath, Map<String, String> dataModel, String sourceFile, String targetFile) {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        try {
            cfg.setDirectoryForTemplateLoading(basePath.toFile());
        } catch (IOException e) {
            log.error("模板加载失败.{}", basePath, e);
            throw BizException.throwExp(ScaffoldError.FTL_PATH_ERROR, basePath.toString());
        }
        cfg.setDefaultEncoding(CharsetUtil.UTF_8);
        File file = FileUtil.file(targetFile);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), CharsetUtil.UTF_8))) {
            Template template = cfg.getTemplate(sourceFile);
            template.process(dataModel, out);
        } catch (Exception e) {
            log.error("模板文件解析失败.源:{},目标:{}", sourceFile, targetFile, e);
            throw BizException.throwExp(ScaffoldError.FTL_PARSE_ERROR, sourceFile + e.getMessage());
        }
    }

    public String buildString(String name, String text, Map<String, String> dataModel) {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(templateLoader);
        templateLoader.putTemplate(name, text);

        cfg.setDefaultEncoding("UTF-8");
        StringWriter writer = new StringWriter();
        try {
            cfg.clearTemplateCache();
            cfg.getTemplate(name).process(dataModel, writer);
        } catch (TemplateException | IOException e) {
            log.error("路径模板字符串解析失败.{}", text, e);
            e.printStackTrace();
            throw BizException.throwExp(ScaffoldError.PATH_PARSE_ERROR, text);

        }
        return writer.toString();
    }
}
