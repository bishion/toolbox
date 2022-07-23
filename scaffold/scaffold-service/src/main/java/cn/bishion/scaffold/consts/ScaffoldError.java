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
    FTL_PARSE_ERROR("010161", "生成模板文件失败.{0}"),
    PATH_PARSE_ERROR("010162", "路径解析失败.{0}"),
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
