package com.zxj.agents.service.impl;

import com.github.pagehelper.PageHelper;
import com.zxj.agents.common.model.PagingModel;
import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.framewrok.utils.ShiroUtil;
import com.zxj.agents.mapper.OperateLogMapper;
import com.zxj.agents.model.OperateLogModel;
import com.zxj.agents.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作员日志业务实现层
 * @author:zhongxiaojian
 * @date:2018-04-10 15:28
 */
@Service("operateLogService")
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public ResultModel listPage(String operatorId, Long start, Long end, String keyword, Integer pageSize, Integer pageNum) {
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
            List<OperateLogModel> list = this.operateLogMapper.selectAll(operatorId, start,end,keyword);
            if (null != list) {
                return new ResultModel<>(200, "分页获取日志信息成功", new PagingModel<>(list));
            } else {
                return new ResultModel<>(501, "分页获取日志信息失败");
            }
        } else {
            return new ResultModel<>(501, "当前页码或每页记录数不能够为空");
        }
    }

    @Override
    public ResultModel add(OperateLogModel model) {
        ResultModel result;
        if (null != model) {
            if (0 == this.operateLogMapper.insert(model)) {
                result = new ResultModel(501, "更新数据错误");
            } else {
                result = new ResultModel(200, "操作成功");
            }
        } else {
            result = new ResultModel(501, "参数不能为空");
        }
        return result;
    }

    @Override
    public ResultModel add(String description) {
        OperateLogModel model = new OperateLogModel(description, ShiroUtil.getAdmin().getId());
        if (0 == this.operateLogMapper.insert(model)) {
            return  new ResultModel(501, "更新数据错误");
        } else {
            return  new ResultModel(200, "操作成功");
        }
    }
}
