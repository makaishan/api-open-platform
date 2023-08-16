package com.mks.luojiaapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mks.luojiaapi.common.ErrorCode;
import com.mks.luojiaapi.exception.BusinessException;
import com.mks.luojiaapi.model.entity.UserInterfaceInfo;
import com.mks.luojiaapi.mapper.UserInterfaceInfoMapper;
import com.mks.luojiaapi.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author lenovo-mr
* @description 针对表【user_interface_info(用户调用接口信息)】的数据库操作Service实现
* @createDate 2023-08-10 20:09:12
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，参数不能为空
        if (add) {
            if(userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        // 有参数则校验
        if(userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }
}




