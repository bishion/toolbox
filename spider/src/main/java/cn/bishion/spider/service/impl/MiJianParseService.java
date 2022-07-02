package cn.bishion.spider.service.impl;

import cn.bishion.spider.consts.BaseConst;
import cn.bishion.spider.service.AbstractHtmlParseService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: guofangbi
 * @date: 2022/6/27-20:31
 * @version: 1.0.0
 */
@Service("miJianParseService")
public class MiJianParseService extends AbstractHtmlParseService {

    private static final String MI_JIAN_PATH = "/spider/miJian/";
    private static final String MI_JIAN_DOMAIN = "http://www.***.com";

    private Set<String> parseNextUrl(TagNode tagNode,String expression) {

        Object[] links = parseByXPath(tagNode, expression);

        return Arrays.stream(links).map(item->{
            TagNode linkNode = (TagNode) item;

            String href = linkNode.getAttributes().get("href");
            return MI_JIAN_DOMAIN + href;
        }).collect(Collectors.toSet());
    }

    protected Set<String> parseAndSaveContent(String url, String content) {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode tagNode = cleaner.clean(content);


        String title = getInnerHtml(cleaner, parseByXPath( tagNode, "//h1[@class='dlm-title']"));
        if (StrUtil.isBlank(title)){
            return Collections.emptySet();
        }
        String fileName = MI_JIAN_PATH + StrUtil.subAfter(url, "www.***.com/", true)
                .replaceAll(BaseConst.SLASH,"-")+".txt";

        FileUtil.writeString(title + FileUtil.getLineSeparator(),
                fileName, Charset.defaultCharset());
        FileUtil.appendString(url + FileUtil.getLineSeparator(),
                fileName, Charset.defaultCharset());

        String author = getText( parseByXPath( tagNode, "//div[@class='dlm-author']/span[1]"));
        FileUtil.appendString(author + FileUtil.getLineSeparator(),
                fileName, Charset.defaultCharset());

        String articleTime = getText(parseByXPath(tagNode, "//div[@class='dlm-author']/span[2]"));
        FileUtil.appendString(articleTime + FileUtil.getLineSeparator(),
                fileName, Charset.defaultCharset());

        String article = getInnerHtml(cleaner, parseByXPath(tagNode, "//div[@class='dlm-main']"));
        FileUtil.appendString(article + FileUtil.getLineSeparator(),
                fileName, Charset.defaultCharset());

        String commentList = getComments(tagNode,"//div[@id='comment']//div[@class='otras-item']");
        FileUtil.appendString(commentList + FileUtil.getLineSeparator(),
                fileName, Charset.defaultCharset());

        return parseNextUrl(tagNode,"//div[@class='talk-main']/div[1]/a");
    }


    private String getComments( TagNode tagNode,String xPath) {
        Object[] comments = parseByXPath( tagNode, xPath);
        StringBuilder commentList = new StringBuilder();
        Arrays.stream(comments).forEach(item -> {
            TagNode commentItem = (TagNode) item;
            CharSequence commentator = commentItem.getChildTagList().get(0).getText();
            CharSequence comment = commentItem.getChildTagList().get(1).getText();
            CharSequence commentTime = commentItem.getChildTagList().get(3).getChildTags()[1].getText();
            commentList.append(commentator).append(BaseConst.SPACE).append(commentTime).append(FileUtil.getLineSeparator());
            commentList.append(comment).append(FileUtil.getLineSeparator());
        });
        return commentList.toString();
    }

    private String getInnerHtml(HtmlCleaner cleaner, Object[] objects) {
        if (ArrayUtil.isNotEmpty(objects)) {
            return cleaner.getInnerHtml((TagNode) objects[0]);
        }
        return BaseConst.BLANK;
    }

    private String getText(Object[] objects) {
        if (ArrayUtil.isNotEmpty(objects)) {
            return ((TagNode) objects[0]).getText().toString();
        }
        return BaseConst.BLANK;
    }

    private Object[] parseByXPath(TagNode tagNode, String xPth) {
        try {
            return tagNode.evaluateXPath(xPth);
        } catch (XPatherException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static void main(String[] args) {
        MiJianParseService service = new MiJianParseService();
        Set<String> urlSet = service.reqAndParseByUrl("http://www.***.com/group/fa/269119.html");
        System.out.println(urlSet);
        System.out.println(StrUtil.subAfter("http://www.***.com/group/yxa/370685.html","www.***.com/",true).replaceAll(BaseConst.SLASH,"-"));
    }
}
