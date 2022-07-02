package ${groupId}.${appSuffix}.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: scaffold
 * @date: ${initDate}
 * @version: 1.0.0
 */
@Service
public class UserInfoManager {
    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfo getByUsername(String username) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUsername, username);
        return userInfoMapper.selectOne(wrapper);
    }
}
