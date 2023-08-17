package com.mks.luojiaapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mks.luojiaapi.common.ErrorCode;
import com.mks.luojiaapi.exception.BusinessException;
import com.mks.luojiaapi.model.entity.UserInterfaceInfo;
import com.mks.luojiaapi.service.InnerUserInterfaceInfoService;
import com.mks.luojiaapi.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 判断
        if(interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 若数据库中没有记录，先创建一条记录
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return userInterfaceInfoService.update(updateWrapper);
    }
}
