package com.zxj.agents.operator.service.impl;

import com.zxj.agents.common.model.ResultModel;
import com.zxj.agents.framewrok.utils.ShiroUtil;
import com.zxj.agents.operator.mapper.OperatorRoleMapper;
import com.zxj.agents.operator.model.OperatorRoleModel;
import com.zxj.agents.operator.service.OperatorRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色业务实现层
 *
 * @author zhongxiaojian
 * @date 2018-04-12 23:25
 */
@Service("operateRoleService")
public class OperatorRoleServiceImpl implements OperatorRoleService {

    @Resource
    private OperatorRoleMapper operatorRoleMapper;
    @Override
    public ResultModel saveOrEdit(OperatorRoleModel model) {
        ResultModel result;
        if(null!=model){
            model.setCreater(ShiroUtil.getAdmin().getId());
            if(0!=operatorRoleMapper.insertOrUpdate(model)){
                result = new ResultModel(200,"操作成功");
            }else{
                result = new ResultModel(500,"更新数据库失败");
            }
        }else{
            result = new ResultModel(500,"参数不能为空");
        }
        return result;
    }

    @Override
    public ResultModel del(List<Integer> ids) {
        ResultModel result;
        if(null!=ids&&!ids.isEmpty()){
            if(0!=operatorRoleMapper.delete(ids)){
                result = new ResultModel(200,"操作成功");
            }else{
                result = new ResultModel(500,"更新数据库失败");
            }
        }else{
            result = new ResultModel(500,"参数不能为空");
        }
        return result;
    }

    @Override
    public ResultModel listAll() {
        List<OperatorRoleModel> list = this.operatorRoleMapper.selectAll();
        if(null==list){
            return new ResultModel(500,"获取数据失败");
        }else{
            return new ResultModel(200,"获取数据成功",list);
        }
    }

    @Override
    public ResultModel info(Integer id) {
        if(null!=id){
            OperatorRoleModel model = this.operatorRoleMapper.selectById(id);
            if(null!=model){
                return new ResultModel(200,"查修成功",model);
            }else{
                return new ResultModel(500,"查询数据失败");
            }
        }else{
            return new ResultModel(500,"参数不能为空");
        }
    }
}
