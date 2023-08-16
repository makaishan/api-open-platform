package com.mks.luojiaapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mks.luojiaapi.model.entity.User;
import com.mks.luojiaapi.model.entity.UserInterfaceInfo;

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 参数校验
     * @param userInterfaceInfo userInterfaceInfo
     * @param add add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);
}
