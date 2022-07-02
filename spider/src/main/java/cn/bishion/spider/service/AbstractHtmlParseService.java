package cn.bishion.spider.service;

import cn.hutool.http.HttpUtil;

import java.util.Set;

/**
 * @author: guofangbi
 * @date: 2022/6/27-20:22
 * @version: 1.0.0
 */
public abstract class AbstractHtmlParseService implements HtmlParseService{

    /**
     * 根据url请求和解析
     *
     * @param url url
     * @return {@link Set}<{@link String}>
     */
    @Override
    public Set<String> reqAndParseByUrl(String url) {
        String content = HttpUtil.get(url);

        return parseAndSaveContent(url,content);
    }
    protected abstract Set<String> parseAndSaveContent(String url,String content) ;

}
