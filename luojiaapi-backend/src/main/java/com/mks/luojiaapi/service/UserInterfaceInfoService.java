package com.mks.luojiaapi.service;

import com.mks.luojiaapi.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lenovo-mr
* @description 针对表【user_interface_info(用户调用接口信息)】的数据库操作Service
* @createDate 2023-08-10 20:09:12
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 参数校验
     * @param userInterfaceInfo userInterfaceInfo
     * @param add add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 用户调用接口成功后更新次数
     * @param interfaceInfoId interfaceInfoId
     * @param userId userId
     * @return boolean
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
