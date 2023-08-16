package com.mks.luojiaapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mks.luojiaapi.common.ErrorCode;
import com.mks.luojiaapi.exception.BusinessException;
import com.mks.luojiaapi.mapper.UserMapper;
import com.mks.luojiaapi.model.entity.User;
import com.mks.luojiaapi.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if(StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(userQueryWrapper);
    }
}
