package com.zxj.agents.controller;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.framewrok.utils.ShiroUtil;
import com.zxj.agents.model.OperateLogModel;
import com.zxj.agents.model.OperatorModel;
import com.zxj.agents.service.OperateLogService;
import com.zxj.agents.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作员控制类
 * @author:zhongxiaojian
 * @date:2018-04-09 18:27
 */
@RestController
@RequestMapping("/admin")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private OperateLogService operateLogService;

    /**
     * 操作员登录
     * @param user
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public Object login(@RequestParam("user")String user, @RequestParam("password")String password){
        ResultModel result = this.operatorService.login(user,password);
        OperateLogModel logModel = new OperateLogModel(user+" 登录: "+ result.isSuccess(),user);
        this.operateLogService.add(logModel);
        return result;
    }

    /**
     * 退出系统
     *
     * @return 处理结果对象的JSON字符串
     */
    @RequestMapping("/logout")
    public Object logout() {
        this.operateLogService.add(ShiroUtil.getAdmin().getId()+" 退出登录: true");
        ResultModel result = operatorService.logout();
        return result;
    }
    /**
     * 修改密码
     * <p>
     * 对密码加密
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 处理结果对象的JSON字符串
     */
    @RequestMapping("/changePassword")
    public Object changePassword(@RequestParam(value = "oldPassword") String oldPassword,
                                 @RequestParam(value = "newPassword") String newPassword) {
        ResultModel result = operatorService.changePassword(oldPassword, newPassword);
        this.operateLogService.add(ShiroUtil.getAdmin().getId()+" 修改密码: "+ result.isSuccess());
        return result;
    }
    /**
     * 保存或修改
     *
     * @param model 操作员对象
     * @return 处理结果对象的JSON字符串
     */
    @RequestMapping("/saveOrEdit")
    public Object saveOrEdit(OperatorModel model) {
        ResultModel result = operatorService.saveOrEdit(model);
        this.operateLogService.add(" 修改或添加操作员 "+model.getId()+" :"+ result.isSuccess());
        return result;
    }

    /**
     * 删除
     *
     * @param id 操作员id
     * @return 处理结果对象的JSON字符串
     */
    @RequestMapping("/del")
    public Object del(@RequestParam(value = "id") String id) {
        ResultModel result = operatorService.delete(id);
        this.operateLogService.add(" 删除操作员 "+id+" :"+ result.isSuccess());
        return result;
    }

    /**
     * 获取当前管理员详情
     *
     * @return 处理结果对象的JSON字符串
     */
    @RequestMapping("/adminInfo")
    public Object adminInfo() {
        return operatorService.getById(ShiroUtil.getAdmin().getId());
    }

    /**
     * 获取详情
     *
     * @param id 操作员id
     * @return 处理结果对象的JSON字符串
     */
    @RequestMapping("/info")
    public Object info(@RequestParam(value = "id") String id) {
        return operatorService.getById(id);
    }

    /**
     * 获取所有记录列表
     *
     * @return 处理结果对象的JSON字符串
     */
    @GetMapping("/listAll")
    public Object listAll() {
        return operatorService.listAll();
    }

}
