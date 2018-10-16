package com.zxj.agents.framewrok.realm;


import com.zxj.agents.operator.mapper.OperatorMapper;
import com.zxj.agents.operator.model.OperatorModel;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作员权限验证类
 *
 * @author zhoudeming
 * @date 2017/7/20
 */
public class OperatorRealm extends AuthorizingRealm {

    @Autowired
    private OperatorMapper operatorMapper;


    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /* OperatorModel operatorModel = (OperatorModel) principals.getPrimaryPrincipal();
         *//*
         *  设置当前管理员拥有的角色
         *  需判断当前管理员所属服务商是否为平台服务商，
         *  是则设置角色为系统管理员，不是则设置角色为服务商管理员
         *//*
        Set<String> roleSet = new HashSet<>();
        if (OperatorConst.SYSTEM_ADMIN_SP.equals(operatorModel.getSp())) {
            roleSet.add("sysAdmin");
        } else {
            roleSet.add("spAdmin");
        }
        *//*
         *  设置当前管理员拥有的权限
         *  需判断当前管理员是否为超级管理员，
         *  是则返回所有权限列表，不是则根据操作员id获取权限列表
         *//*
        String operatorId = operatorModel.getId();
        List<MenuModel> menuModelList;
        if (OperatorConst.SUPER_ADMIN_ID.equals(operatorId)) {
            menuModelList = menuMapper.selectAll(null, null);
        } else {
            menuModelList = menuMapper.selectByOperatorId(operatorId);
        }
        Set<String> permissionSet = new HashSet<>();
        String permission;
        for (MenuModel menuModel : menuModelList) {
            permission = menuModel.getPermission();
            if (StringUtils.isNotBlank(permission)) {
                permissionSet.addAll(Arrays.asList(permission.trim().split(",")));
            }
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionSet);*/
        return null;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String operatorId = (String) token.getPrincipal();
        String operatorPassword = new String((char[]) token.getCredentials());
        OperatorModel operatorModel = operatorMapper.selectById(operatorId);
        if (null == operatorModel) {
            throw new UnknownAccountException("账号不存在");
        } else if (!operatorPassword.equals(operatorModel.getPassword())) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        return new SimpleAuthenticationInfo(operatorModel, operatorPassword, getName());
    }

}
