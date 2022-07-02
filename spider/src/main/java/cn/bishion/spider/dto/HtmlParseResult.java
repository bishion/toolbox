package cn.bishion.spider.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author: guofangbi
 * @date: 2022/6/27-20:28
 * @version: 1.0.0
 */@Getter
@Setter
public class HtmlParseResult {
     private String content;
     private Set<String> urlSet;

     
}
