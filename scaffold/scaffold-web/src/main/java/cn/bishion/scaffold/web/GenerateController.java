package cn.bishion.scaffold.web;

import cn.bishion.scaffold.service.FreeMarkerService;
import cn.bishion.scaffold.service.PathParseService;
import cn.bishion.scaffold.service.dto.GenerateRequest;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.dto.BaseResult;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/**
 * @author: guofangbi
 * @date: 2022/6/19-22:19
 * @version: 1.0.0
 */
@RestController
public class GenerateController {


    @Resource
    private FreeMarkerService freeMarkerService;
    @Resource
    private PathParseService pathParseService;
    File templateLocate = new File("D:/Workspace/toolbox/scaffold/template/default");

    private static final String FIELD_APP_CODE = "appCode";
    private static final String FIELD_GROUP_ID = "groupId";
    private static final String FIELD_SUFFIX = "appSuffix";

    @SneakyThrows
    @PostMapping("/generate")
    public BaseResult<Void> generate(GenerateRequest request) {
        String basePath = templateLocate.getPath();
        Map<String, Object> param = assemParam(request);


        String baseTargetFile = new File("D:/test/" + IdUtil.fastSimpleUUID()).getPath() + File.separator;
        Files.walk(templateLocate.toPath()).filter(item -> !Files.isDirectory(item))
                .forEach(item -> {
                    String fileName = item.toFile().getName();
                    String dirName = item.getParent().toString();
                    String relativePath = CharSequenceUtil.subAfter(dirName, basePath, false);
                    String targetDir = baseTargetFile + param.get(FIELD_APP_CODE) + relativePath + File.separator;
                    targetDir = pathParseService.parsePath(targetDir, param);
                    new File(targetDir).mkdirs();
                    String targetFile = targetDir + fileName;

                    freeMarkerService.buildFile(param, relativePath + File.separator + fileName, targetFile);

                });

        return BaseResult.success();
    }

    private Map<String, Object> assemParam(GenerateRequest request) {
        Map<String, Object> param = Maps.newHashMapWithExpectedSize(BaseConst.INT_3);
        if (CharSequenceUtil.isBlank(request.getGroupId())) {
            param.put(FIELD_GROUP_ID, "cn.bishion");

        }
        if (CharSequenceUtil.isBlank(request.getAppCode())) {
            param.put(FIELD_APP_CODE, "demo");
        }
        String appCode = param.get(FIELD_APP_CODE).toString();
        param.put(FIELD_SUFFIX, StrUtil.contains(appCode, BaseConst.HYPHEN) ?
                appCode.split(BaseConst.HYPHEN)[BaseConst.INT_1] : appCode);
        return param;
    }

}
