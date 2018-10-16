package com.zxj.agents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 操作员日志
 * @author:zhongxiaojian
 * @date:2018-04-10 15:30
 */
@Data
public class OperateLogModel {
    /**
     * 自增索引
     */
    private Long idx;
    /**
     *操作员id
     */
    private String operatorId;
    /**
     *操作时间
     */
    private Long utc;
    /**
     *描述
     */
    private String description;

    public OperateLogModel() {
    }

    public OperateLogModel( String description,String operatorId) {
        this.operatorId = operatorId;
        this.description = description;
    }

}
