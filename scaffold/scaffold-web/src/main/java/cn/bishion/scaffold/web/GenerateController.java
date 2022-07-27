package cn.bishion.scaffold.web;

import cn.bishion.scaffold.consts.ScaffoldConst;
import cn.bishion.scaffold.consts.ScaffoldError;
import cn.bishion.scaffold.service.GenerateService;
import cn.bishion.scaffold.service.dto.GenerateRequest;
import cn.bishion.toolkit.common.consts.BaseConst;
import cn.bishion.toolkit.common.dto.BaseResult;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * @author: Bishion
 * @date: 2022/6/19-22:19
 * @version: 1.0.0
 */
@Slf4j
@Controller
public class GenerateController {

    @Value("${scaffold.defaultGroup}")
    private String defaultGroup;
    @Value("${scaffold.defaultApp}")
    private String defaultApp;
    @Resource
    private GenerateService generateService;

    @PostMapping("/generate")
    public void generate(@RequestBody GenerateRequest request,HttpServletResponse response) {
        Map<String, String> param = assemParam(request);
        File file = generateService.buildAndZip(param);
        String appCode = param.get(ScaffoldConst.FIELD_APP_CODE);

        try (ServletOutputStream outputStream = response.getOutputStream()) {

            prepareToDownload(response, file.getName(), file.length());
            outputStream.write(FileUtil.readBytes(file));
        } catch (Exception e) {
            log.error("文件生成成功，下载失败. {}", request, e);
            downloadError(response, appCode,e);
        }
    }

    private Map<String, String> assemParam(GenerateRequest request) {
        Map<String, String> param = Maps.newHashMapWithExpectedSize(BaseConst.INT_8);

        // 设置 groupId
        paramPut(param,ScaffoldConst.FIELD_GROUP_ID,request.getGroupId(),defaultGroup);

        String appCode = CharSequenceUtil.isBlank(request.getAppCode())?defaultApp: request.getAppCode();
        // 设置 appCode
        param.put(ScaffoldConst.FIELD_APP_CODE, appCode);
        // 设置 appDesc
        paramPut(param,ScaffoldConst.FIELD_APP_DESC,request.getAppDesc(),appCode);
        // 设置 appName
        paramPut(param,ScaffoldConst.FIELD_APP_NAME,request.getAppName(),appCode);
        // 设置 initDate
        param.put(ScaffoldConst.FIELD_INIT_DATE, DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        // 设置 appStyle
        paramPut(param,ScaffoldConst.FIELD_APP_STYLE, request.getAppStyle(),ScaffoldConst.DEFAULT_APP_STYLE);
        // 设置 subPkg
        String subPkg = StrUtil.contains(appCode, BaseConst.HYPHEN) ?
                appCode.split(BaseConst.HYPHEN)[BaseConst.INT_1] : appCode;
        param.put(ScaffoldConst.FIELD_SUB_PKG, subPkg);   // 设置 subPkg

        String subPkgUpper = CharSequenceUtil.upperFirst(subPkg);
        param.put(ScaffoldConst.FIELD_SUB_PKG_UPPER, subPkgUpper);

        return param;
    }

    private void paramPut(Map<String,String> param, String key,String value,String defaultValue){
        if (CharSequenceUtil.isBlank(value)){
            param.put(key,defaultValue);
        }else{
            param.put(key, value);
        }
    }
    /**
     * 准备下载
     * //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
     * //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
     * // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
     *
     * @param response 响应
     */
    private void prepareToDownload(HttpServletResponse response, String fileName, Long length) {
        response.reset();

        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.addHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileName, CharsetUtil.CHARSET_UTF_8));

        response.addHeader("Content-Length", "" + length);
        response.setContentType("application/octet-stream");
    }
    private void downloadError(HttpServletResponse response,String appCode, Exception exception){
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try{
            response.getWriter().write(JSONUtil.toJsonStr(BaseResult.fail(ScaffoldError.ZIP_DOWNLOAD_ERROR, appCode,
                    exception.getMessage())));
        }catch (Exception e){
            log.error("下载出错, 写入返回值失败. app:{}",appCode,e);
        }

    }

}