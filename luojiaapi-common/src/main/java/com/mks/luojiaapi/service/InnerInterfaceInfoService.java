package com.mks.luojiaapi.service;

import com.mks.luojiaapi.model.entity.InterfaceInfo;

/**
* @author lenovo-mr
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-04 22:01:51
*/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询接口是否存在（请求路径，请求方法）
     * @param path path
     * @param method method
     * @return InterfaceInfo
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
