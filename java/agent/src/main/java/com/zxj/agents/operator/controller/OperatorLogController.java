package com.zxj.agents.operator.controller;

import com.zxj.agents.operator.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作员日志控制类
 *
 * @author zhongxiaojian
 * @date 2018-04-12 23:10
 */
@RequestMapping("/operator/log")
@RestController
public class OperatorLogController {

    @Autowired
    private OperateLogService operateLogService;

    @RequestMapping("/listPage")
    public Object listAll(@RequestParam(value = "operatorId",required = false) String operatorId,
                          @RequestParam(value = "start",required = false) Long start,
                          @RequestParam(value = "end",required = false) Long end,
                          @RequestParam(value = "keyword" ,required = false) String keyword,
                          @RequestParam(value = "pageSize") Integer pageSize,
                          @RequestParam(value = "pageNum") Integer pageNum){
        return this.operateLogService.listPage(operatorId,start,end,keyword,pageSize,pageNum);
    }
}
