package cn.bishion.scaffold.demo.service.demo1.db.repository;

import cn.bishion.scaffold.demo.service.demo1.db.entity.UserInfo;
import cn.bishion.scaffold.demo.service.demo1.db.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: guofangbi
 * @date: 2022/5/28-7:14
 * @version: 1.0.0
 */
@Service
public class UserInfoRepository {
    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfo getByUsername(String username) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUsername, username);
        return userInfoMapper.selectOne(wrapper);
    }
}
