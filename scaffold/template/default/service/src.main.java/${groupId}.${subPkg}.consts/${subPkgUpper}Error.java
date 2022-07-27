package ${groupId}.${subPkg}.consts;

import cn.bishion.toolkit.common.consts.BaseError;

/**
 * 错误信息
 *
 * @author: scaffold
 * @date: ${initDate}
 * @version: 1.0.0
 */
public enum ${subPkgUpper}Error implements BaseError {

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
