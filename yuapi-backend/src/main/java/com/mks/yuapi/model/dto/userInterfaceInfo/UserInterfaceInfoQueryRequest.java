package com.mks.yuapi.model.dto.userInterfaceInfo;


import com.mks.yuapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;


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