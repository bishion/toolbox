package cn.bishion.scaffold.demo.service.demo1.north;

import cn.bishion.scaffold.demo.service.demo1.dto.UserInfoDTO;

/**
 * 用户信息服务
 *
 * @author: Bishion
 * @date: 2022-05-27 23:24:08
 * @version: 1.0.0
 */
public interface UserInfoService {
    /**
     * 登录
     *
     * @param userInfoDTO 用户信息传输对象
     * @return boolean
     */
    boolean login(UserInfoDTO userInfoDTO);

}
