package com.mks.luojiaapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mks.luojiaapi.common.ErrorCode;
import com.mks.luojiaapi.exception.BusinessException;
import com.mks.luojiaapi.mapper.InterfaceInfoMapper;
import com.mks.luojiaapi.model.entity.InterfaceInfo;
import com.mks.luojiaapi.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if(StringUtils.isAnyBlank(url, method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("url", url);
        interfaceInfoQueryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
    }
}
