package cn.bishion.pier.entity;


import cn.bishion.toolkit.common.util.ToString;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author: init
 * @date: 2022-08-09
 * @version: 1.0.0
 */

public class UserInfo implements Serializable {
    private Long id;
    private String username;
    private String loginName;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
