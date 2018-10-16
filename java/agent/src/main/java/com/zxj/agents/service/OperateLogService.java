package com.zxj.agents.service;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.model.OperateLogModel;

/**
 * 操作员日志业务层接口
 *
 * @author:zhongxiaojian
 * @date:2018-04-10 15:27
 */
public interface OperateLogService {

    /**
     * 查询日志
     * @param operatorId
     * @param start
     * @param end
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    ResultModel listPage(String operatorId, Long start, Long end, String keyword, Integer pageSize, Integer pageNum);

    /**
     * 添加
     * @param model
     * @return
     */
    ResultModel add(OperateLogModel model);

    /**
     * 添加
     * @param description
     * @return
     */
    ResultModel add(String description);
}
