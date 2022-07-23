package cn.bishion.scaffold.demo.web;

import cn.bishion.scaffold.demo.service.demo1.dto.UserInfoDTO;
import cn.bishion.scaffold.demo.service.demo1.north.UserInfoService;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: Bishion
 * @date: 2022/5/28-7:21
 * @version: 1.0.0
 */
@Api(tags = "用户信息服务")
@RestController("/user")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public boolean login(@RequestBody UserInfoDTO userInfo) {
        Assert.isTrue(Objects.nonNull(userInfo) &&
                        CharSequenceUtil.isAllNotBlank(userInfo.getUsername(), userInfo.getPassword()),
                "用户名或密码不能为空");
        return userInfoService.login(userInfo);
    }
}
