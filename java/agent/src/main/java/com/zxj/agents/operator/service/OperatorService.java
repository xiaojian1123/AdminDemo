package com.zxj.agents.operator.service;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.operator.model.OperatorModel;

import java.util.List;

/**
 * 操作员业务接口
 *
 * @author:zhongxiaojian
 * @date:2018-04-10 15:31
 */
public interface OperatorService {

    /**
     * 登录
     * <p>
     * 登录成功后需更新登录时间
     *
     * @param id       操作员id
     * @param password 密码
     * @return 处理结果对象
     */
    ResultModel login(String id, String password);

    /**
     * 退出登录
     *
     * @return 处理结果对象
     */
    ResultModel logout();

    /**
     * 修改登录密码
     * <p>
     * 比较旧密码是否跟当前管理员密码相等，相等则修改密码为新密码。
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 处理结果对象
     */
    ResultModel changePassword(String oldPassword, String newPassword);

    /**
     * 保存或修改
     * <p>
     * 需判断当前管理员所属服务商是否是平台服务商，是则通过前台设置操作员所属服务商，不是则设置操作员所属服务商为当前管理员所属服务商；
     * 需判断操作员是否为超级管理员，是则不处理，不是则设置创建者为当前管理员id，并根据操作员id删除原有操作员角色关联关系，同时插入新的操作员角色关联关系。
     *
     * @param model 操作员对象
     * @return 处理结果对象
     */
    ResultModel saveOrEdit(OperatorModel model);

    /**
     * 删除
     *
     * @param id 操作员id
     * @return 处理结果对象
     */
    ResultModel delete(String id);

    /**
     * 根据id获取详情
     *
     * @param id 操作员id
     * @return 处理结果对象
     */
    ResultModel<OperatorModel> getById(String id);

    /**
     * 获取所有记录列表
     * <p>
     * 只能返回当前管理员创建的操作员记录
     * @return 处理结果对象
     */
    ResultModel<List<OperatorModel>> listAll();
}
