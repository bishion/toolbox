package cn.bishion.scaffold.service;

import cn.bishion.scaffold.consts.ScaffoldConst;
import cn.bishion.scaffold.consts.ScaffoldError;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.dto.BizException;
import cn.bishion.toolkit.common.util.ToString;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author: Bishion
 * @date: 2022/6/21-10:39
 * @version: 1.0.0
 */
@Slf4j
@Service
public class GenerateService {
    @Value("${scaffold.tmpFolder}")
    private String tmpFolder;
    @Value("${scaffold.templateFolder}")
    private String templateFolder;
    @Resource
    private FreeMarkerService freeMarkerService;

    public File buildAndZip(Map<String, String> param) {
        // 应用代码存放目录
        String targetCodeFolder = new File(tmpFolder + IdUtil.fastSimpleUUID()).getPath() + File.separator +
                param.get(ScaffoldConst.FIELD_APP_CODE) + File.separator;

        buildByTemplate(targetCodeFolder, param);
        return ZipUtil.zip(targetCodeFolder);
    }

    public void buildByTemplate(String targetCodeFolder, Map<String, String> param) {
        try {
            String appTemplateFolder = this.templateFolder + param.get(ScaffoldConst.FIELD_APP_STYLE) + File.separator;
            Path templateFolderPath = Paths.get(appTemplateFolder);
            Files.walk(templateFolderPath).filter(item -> !Files.isDirectory(item) && !item.toFile().isHidden())
                    .forEach(item -> {
                        String sourceFile = CharSequenceUtil.subAfter(item.toString(), appTemplateFolder, false);
                        String targetFile = targetCodeFolder + buildTargetFile(item, appTemplateFolder, param);
                        FileUtil.mkParentDirs(targetFile);
                        freeMarkerService.buildFile(templateFolderPath, param, sourceFile, targetFile);
                    });
        } catch (BizException e) {
            throw e;
        } catch (IOException e) {
            log.error("文件遍历失败.{}", ToString.toString(param), e);
            throw BizException.throwExp(ScaffoldError.PATH_PARSE_ERROR, e.getMessage());
        } catch (Exception e) {
            log.error("代码生成失败,未知异常.{}", ToString.toString(param), e);
            throw BizException.throwExp(ScaffoldError.PATH_PARSE_ERROR, param.get(ScaffoldConst.FIELD_APP_CODE), e.getMessage());
        }
    }

    private String buildTargetFile(Path sourceFilePath, String appTemplateFolder, Map<String, String> param) {
        String fileName = sourceFilePath.getFileName().toString();
        String parentFolder = sourceFilePath.getParent().toString();
        String relativeFolder = CharSequenceUtil.subAfter(parentFolder, appTemplateFolder, false);
        String relativeSourceFileWithApp = CharSequenceUtil.subAfter(sourceFilePath.toString(), templateFolder, false);
        String targetFileFolder = freeMarkerService.buildString(relativeFolder, relativeFolder, param);
        String targetFileName = freeMarkerService.buildString(relativeSourceFileWithApp, fileName, param);

        return StrUtil.replace(targetFileFolder, BaseConst.DOT, File.separator) + File.separator + targetFileName;
    }
}
