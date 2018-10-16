package com.zxj.agents.model;

import lombok.Data;

/**
 * 操作员员Model
 * @author:zhongxiaojian
 * @date:2018-04-09 16:33
 */
@Data
public class OperatorModel {

    /**
     * 自定义id
     */
    private String id;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 角色名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String phone;
    /**
     * 地址
     */
    private String addr;
    /**
     * 登录时间
     */
    private Long loginUtc;

    /**
     * 创建者id
     */
    private String creater;

}
