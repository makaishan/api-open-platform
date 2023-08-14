package com.mks.yuapi.service;

import com.mks.yuapi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mks.yuapi.model.entity.Post;

/**
* @author lenovo-mr
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-04 22:01:51
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
