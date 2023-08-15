package com.mks.luojiaapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mks.luojiaapi.common.ErrorCode;
import com.mks.luojiaapi.exception.BusinessException;
import com.mks.luojiaapi.exception.ThrowUtils;
import com.mks.luojiaapi.mapper.InterfaceInfoMapper;
import com.mks.luojiaapi.model.entity.InterfaceInfo;
import com.mks.luojiaapi.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author lenovo-mr
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-08-04 22:01:51
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
        }
    }
}




