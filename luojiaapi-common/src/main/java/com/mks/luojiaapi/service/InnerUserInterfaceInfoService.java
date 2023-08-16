package com.mks.luojiaapi.service;

/**
* @author lenovo-mr
* @description 针对表【user_interface_info(用户调用接口信息)】的数据库操作Service
* @createDate 2023-08-10 20:09:12
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 用户调用接口成功后更新次数
     * @param interfaceInfoId interfaceInfoId
     * @param userId userId
     * @return boolean
     */
    boolean invokeCount(long interfaceInfoId, long userId);

}
