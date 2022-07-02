package cn.bishion.scaffold.service;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author: guofangbi
 * @date: 2022/6/12-21:17
 * @version: 1.0.0
 */
@Slf4j
@Service
public class PathParseService {
    public String parsePath(String path, Map<String, Object> paramMap) {
        path = StrUtil.replace(path, "${appSuffix}", paramMap.get("appSuffix").toString());
        path = StrUtil.replace(path, "${groupId}", paramMap.get("groupId").toString());
        return path.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
    }
}
