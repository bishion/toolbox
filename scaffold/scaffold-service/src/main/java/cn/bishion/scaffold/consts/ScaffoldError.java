package cn.bishion.scaffold.consts;

import cn.bishion.toolkit.common.consts.BaseError;

/**
 * @author: Bishion
 * @date: 2022/6/20-9:19
 * @version: 1.0.0
 */
public enum ScaffoldError implements BaseError {
    /**
     * ftl解析错误
     */
    FTL_PARSE_ERROR("010161", "模板文件解析失败.{0}"),
    PATH_PARSE_ERROR("010162", "路径解析失败.{0}"),
    GENERATE_CODE_ERR("010163", "代码文件生成失败，未知异常.{0}-{1}"),
    ZIP_DOWNLOAD_ERROR("010164", "代码文件已生成，下载失败，未知异常.{0}-{1}"),
    FTL_PATH_ERROR("010165", "模板文件加载失败.{0}"),
    STRING_PARSE_ERROR("010166", "模板字符串解析失败.{0}"),
    DOMAIN_EQ_PKG("010167", "核心包名不能和核心模块名相同.{0}"),
    ;
    private String code;
    private String pattern;

    ScaffoldError(String code, String pattern) {
        this.code = code;
        this.pattern = pattern;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
