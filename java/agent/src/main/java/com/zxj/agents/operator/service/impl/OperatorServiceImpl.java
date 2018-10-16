package com.zxj.agents.operator.service.impl;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.framewrok.utils.ShiroUtil;
import com.zxj.agents.operator.mapper.OperatorMapper;
import com.zxj.agents.operator.model.OperatorModel;
import com.zxj.agents.operator.service.OperatorService;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 操作员业务实现接口
 * @author:zhongxiaojian
 * @date:2018-04-10 15:32
 */
@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public ResultModel login(String id, String password) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(password)) {
            try {
                Subject subject = ShiroUtil.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(id, password);
                subject.login(token);
                operatorMapper.updateLoginUtcById(id);
                return new ResultModel(200, "登录成功",ShiroUtil.getAdmin());
            } catch (UnknownAccountException e) {
                return new ResultModel(501, e.getMessage());
            } catch (IncorrectCredentialsException e) {
                return new ResultModel(501, e.getMessage());
            } catch (LockedAccountException e) {
                return new ResultModel(501, e.getMessage());
            } catch (AuthenticationException e) {
                return new ResultModel(501, e.getMessage());
            }
        } else {
            return new ResultModel(501, "操作员id或密码不能够为空");
        }
    }

    @Override
    public ResultModel logout() {
        ShiroUtil.logout();
        return new ResultModel(200, "退出成功");
    }

    @Override
    public ResultModel changePassword(String oldPassword, String newPassword) {
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            OperatorModel adminModel = ShiroUtil.getAdmin();
            if (adminModel.getPassword().equals(oldPassword)) {
                if (0 != operatorMapper.updatePasswordById(adminModel.getId(), newPassword)) {
                    return new ResultModel(200, "修改密码成功");
                } else {
                    return new ResultModel(500, "修改密码失败");
                }
            } else {
                return new ResultModel(500, "旧密码不正确");
            }
        } else {
            return new ResultModel(500, "旧密码或新密码不能够为空");
        }
    }

    @Override
    @Transactional
    public ResultModel saveOrEdit(OperatorModel model) {
        if (null != model) {
            if (null == model.getCreater() || StringUtils.isBlank(model.getCreater())) {
                model.setCreater(ShiroUtil.getAdmin().getId());
            }
            if (0 != operatorMapper.insertOrUpdate(model)) {
                return new ResultModel(200, "保存或修改操作员信息成功");
            } else {
                return new ResultModel(500, "保存或修改操作员信息失败");
            }
        } else {
            return new ResultModel(500, "操作员对象不能够为空");
        }
    }

    @Override
    public ResultModel delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            if (0 != operatorMapper.delete(id)) {
                return new ResultModel(200, "删除操作员信息成功");
            } else {
                return new ResultModel(500, "删除操作员信息失败");
            }
        } else {
            return new ResultModel(500, "操作员id不能够为空");
        }
    }

    @Override
    public ResultModel<OperatorModel> getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            OperatorModel operatorModel = operatorMapper.selectById(id);
            if (null != operatorModel) {
                return new ResultModel<>(200, "获取操作员信息成功", operatorModel);
            } else {
                return new ResultModel<>(501, "获取操作员信息失败");
            }
        } else {
            return new ResultModel<>(500, "操作员id不能够为空");
        }
    }

    @Override
    public ResultModel<List<OperatorModel>> listAll() {
        List<OperatorModel> operatorModelList = operatorMapper.selectByCreatorId(ShiroUtil.getAdmin().getId());
        if (null != operatorModelList) {
            return new ResultModel<>(200, "获取操作员信息列表成功", operatorModelList);
        } else {
            return new ResultModel<>(500, "获取操作员信息列表失败");
        }
    }
}
