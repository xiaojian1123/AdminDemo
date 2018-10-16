package com.zxj.agents.framewrok.filter;

import com.alibaba.fastjson.JSON;
import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.framewrok.utils.ShiroUtil;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 重写 authc 拦截器
 *
 * @author xiaojian
 * @date 2018/3/12
 */
public class AuthenticationFilter extends AccessControlFilter {

    /**
     * 是否允许访问，允许访问返回true，否则false；
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        ResultModel resultModel = new ResultModel();
        response.setCharacterEncoding("UTF-8");
        if (ShiroUtil.getAdmin() == null) {
            resultModel.setCode(401);
            resultModel.setMsg("登录超时");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            PrintWriter outPrintWriter = httpResponse.getWriter();
            outPrintWriter.println(JSON.toJSONString(resultModel));
            outPrintWriter.flush();
            outPrintWriter.close();
            this.saveRequest(request);
            return false;
        }
        return true;
    }

    /**
     *拒绝访问时处理是否需要继续处理 (false 已经处理直接返回，true 需要继续处理）
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }
}
