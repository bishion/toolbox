package cn.bishion.spider.consts;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author: guofangbi
 * @date: 2022/6/27-20:34
 * @version: 1.0.0
 */
@Getter
public enum SiteType {
    /**
     * http://www.***.com/group/fa/391879.html
     * http://www.***.com/group/yxa/391536.html
     */
    MI_JIAN("miJianParseService", Arrays.asList("http://www.***.com/group/fa/391879.html",
            "http://www.***.com/group/yxa/391536.html","http://www.***.com/group/fa/391873.html",
            "http://www.***.com/group/fa/391969.html","http://www.***.com/group/fa/391882.html",
            "http://www.***.com/group/fa/391883.html","http://www.***.com/help/255814.html"));
    private String serviceName;

    private List<String> enterUrl;

    SiteType(String serviceName, List<String> enterUrl) {
        this.serviceName = serviceName;
        this.enterUrl = enterUrl;
    }

}
