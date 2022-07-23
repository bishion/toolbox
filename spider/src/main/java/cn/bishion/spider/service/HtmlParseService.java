package cn.bishion.spider.service;

import java.util.Set;

/**
 * @author: Bishion
 * @date: 2022/6/27-20:22
 * @version: 1.0.0
 */
public interface HtmlParseService {

    /**
     * 根据url请求和解析
     *
     * @param url url
     * @return {@link Set}<{@link String}>
     */
    Set<String> reqAndParseByUrl(String url);
}
