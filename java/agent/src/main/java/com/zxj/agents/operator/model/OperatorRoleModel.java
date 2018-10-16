package com.zxj.agents.operator.model;

import lombok.Data;

/**
 * 角色模型
 *
 * @author zhongxiaojian
 * @date 2018-04-12 23:24
 */
@Data
public class OperatorRoleModel {
    /**
     * 角色ID，自动增长
     */
    private Integer idx;
    /**
     *角色名
     */
    private String name;
    /**
     *权限(权限具体与各个管理模块ID对应，
     * 拥有该模块ID串则具有该操作权限，
     * ID间用"/"竖杠隔开，*号表示可使用所有权限（超级用户）)
     */
    private String permission;
    /**
     *创建者
     */
    private String creater;
}
