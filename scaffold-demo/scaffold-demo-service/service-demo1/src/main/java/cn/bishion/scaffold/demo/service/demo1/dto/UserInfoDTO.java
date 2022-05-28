package cn.bishion.scaffold.demo.service.demo1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 这里存放当前服务的数据传输对象
 *
 * @author: guofangbi
 * @date: 2022-05-27 23:19:20
 * @version: 1.0.0
 */
@ApiModel(description = "用户信息")
public class UserInfoDTO {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
