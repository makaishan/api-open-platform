package com.mks.luojiaapi.model.dto.userInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 调用接口用户Id
     */
    private Long userId;

    /**
     * 接口Id
     */
    private Long interfaceInfoId;

    /**
     * 已调用次数
     */
    private Integer totalNum;

    /**
     * 剩余可调用次数
     */
    private Integer leftNum;

    /**
     * 调用状态（0-正常，1-禁用）
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}