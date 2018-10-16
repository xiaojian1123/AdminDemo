package com.zxj.agents.operator.service;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.operator.model.OperatorRoleModel;

import java.util.List;

/**
 * 角色业务接口层
 *
 * @author zhongxiaojian
 * @date 2018-04-12 23:22
 */

public interface OperatorRoleService {
    /**
     * 添加或修改
     * @param model 操作角色对象
     * @return
     */
    ResultModel saveOrEdit(OperatorRoleModel model);

    /**
     * 根据ids删除数据
     * @param ids idx 的集合
     * @return
     */
    ResultModel del(List<Integer> ids);

    /**
     * 查询所有的数据
     * @return
     */
    ResultModel listAll();

    /**
     * 根据id查找数据
     * @param id
     * @return
     */
    ResultModel info(Integer id);
}
