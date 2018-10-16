package com.zxj.agents.framewrok.utils;

import com.zxj.agents.operator.model.OperatorModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro帮助类
 *
 * @author zhoud
 * @date 2017/8/8
 */
public class ShiroUtil {

    /**
     * 获取shiro认证组件对象
     *
     * @return shiro认证组件对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前会话
     *
     * @return 会话对象
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前管理员
     *
     * @return 管理员对象
     */
    public static OperatorModel getAdmin() {
        return (OperatorModel) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
}
