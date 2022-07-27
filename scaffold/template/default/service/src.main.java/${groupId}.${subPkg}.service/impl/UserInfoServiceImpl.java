package ${groupId}.${subPkg}.service;

import ${groupId}.${subPkg}.manager.entity.UserInfo;
import ${groupId}.${subPkg}.manager.UserInfoManager;
import ${groupId}.${subPkg}.dto.UserInfoDTO;
import ${groupId}.${subPkg}.service.UserInfoService;
import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户信息服务实现
 *
 * @author: Bishion
 * @date: 2022-05-27 23:24:43
 * @version: 1.0.0
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean login(UserInfoDTO userInfoDTO) {

        UserInfo userInfo = userInfoRepository.getByUsername(userInfoDTO.getUsername());

        return Objects.nonNull(userInfo) && CharSequenceUtil.equals(userInfo.getPassword(), userInfoDTO.getPassword());

    }
}
