package com.zxj.agents.service.impl;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.framewrok.utils.ShiroUtil;
import com.zxj.agents.mapper.OperatorMapper;
import com.zxj.agents.model.OperatorModel;
import com.zxj.agents.service.OperatorService;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

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
        return null;
    }

    @Override
    public ResultModel changePassword(String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public ResultModel saveOrEdit(OperatorModel model) {
        return null;
    }

    @Override
    public ResultModel delete(String id) {
        return null;
    }

    @Override
    public ResultModel<OperatorModel> getById(String id) {
        return null;
    }

    @Override
    public ResultModel<List<OperatorModel>> listAll() {
        return null;
    }
}
