package com.zxj.agents.operator.controller;

import com.zxj.agents.operator.model.OperatorRoleModel;
import com.zxj.agents.operator.service.OperatorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理控制层
 *
 * @author zhongxiaojian
 * @date 2018-04-12 23:18
 */
@RestController
@RequestMapping("/operator/role")
public class OperatorRoleController {

    @Autowired
    private OperatorRoleService operatorRoleService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/listAll")
    public Object listAll() {
        return this.operatorRoleService.listAll();
    }
    @RequestMapping("info")
    public Object info(@RequestParam(name = "id") Integer id){
        return this.operatorRoleService.info(id);
    }
    /**
     * 添加或修改
     *  系统设置当前操作员为creater
     * @param model
     * @return
     */
    @RequestMapping("/saveOrEdit")
    public Object saveOrEdit(OperatorRoleModel model) {
        return this.operatorRoleService.saveOrEdit(model);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("del")
    public Object del(@RequestParam(name = "ids[]") List<Integer> ids) {
        return this.operatorRoleService.del(ids);
    }
}
