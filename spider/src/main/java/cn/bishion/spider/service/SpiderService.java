package cn.bishion.spider.service;

import cn.bishion.spider.consts.SiteType;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author: Bishion
 * @date: 2022/6/27-20:18
 * @version: 1.0.0
 */@Service
@Slf4j
public class SpiderService {
    public Set<String> urlSet = new HashSet<>();
    private static final String URL_PAH = "/alidata1/admin/spider/miJianUrl.txt";
    @Resource
    private Map<String, HtmlParseService> htmlParseServiceMap;

    private static final List<String> MI_JIAN_BASE_URL = Arrays.asList("http://www.**********.com/group/fa/"
/*            "http://www.**********.com/group/yxa/",
            "http://www.**********.com/help/"*/);
    public void spiderByEnterUrl(SiteType siteType){
        if (!FileUtil.exist(URL_PAH)){
            FileUtil.newFile(URL_PAH);
        }
        FileUtil.readUtf8Lines(URL_PAH).parallelStream().forEach(item -> urlSet.add(item));
        siteType.getEnterUrl().stream().forEach(url->
        this.spider(url, htmlParseServiceMap.get(siteType.getServiceName()))
        );
    }
    public void spiderMiJianPageById(SiteType siteType,int startId, int endId){
        if (!FileUtil.exist(URL_PAH)){
            FileUtil.newFile(URL_PAH);
        }

        FileUtil.readUtf8Lines(URL_PAH).parallelStream().forEach(item -> urlSet.add(item));
        MI_JIAN_BASE_URL.stream().forEach(item->{
            for (int i = startId;i<endId;i++){
                String url = item + i + ".html";
                if (urlSet.contains(url)){
                    continue;
                }
                saveUrlAndSleep(url);
                spider(url, htmlParseServiceMap.get(siteType.getServiceName()));
            }
        });
    }

    private void spider(String url,HtmlParseService htmlParseService){

        Set<String> nextUrlSet=  htmlParseService.reqAndParseByUrl(url);
        System.out.println("解析一条："+url);
        nextUrlSet.stream().filter(item->!urlSet.contains(item)).forEach(item->{
            saveUrlAndSleep(item);
            spider(item, htmlParseService);
        });

    }

    private void saveUrlAndSleep(String url){
        urlSet.add(url);
        FileUtil.appendString(url + FileUtil.getLineSeparator(), URL_PAH, Charset.defaultCharset());
        ThreadUtil.sleep(RandomUtil.randomLong(100));
    }

}
