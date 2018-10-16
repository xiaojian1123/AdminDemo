package com.zxj.agents.operator.mapper;

import com.zxj.agents.operator.model.OperatorRoleModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 角色持久层
 *
 * @author zhongxiaojian
 * @date 2018-04-12 23:28
 */
@Mapper
public interface OperatorRoleMapper {
    /**
     * 添加或更新操作员角色
     * @param model
     * @return
     */
    @Insert("insert into operator_role" +
            "        (idx,name,permission,creater)" +
            "        values" +
            "        (#{model.idx},#{model.name},#{model.permission},#{model.creater})" +
            "        ON DUPLICATE KEY UPDATE" +
            "        name = #{model.name}," +
            "        permission = #{model.permission}," +
            "        creater = #{model.creater}")
    int insertOrUpdate(@Param("model") OperatorRoleModel model);

    /**
     * 根据idx删除数据
     * @param ids idx的集合
     * @return
     */
    @Delete("<script>" +
            "   delete from operator_role where idx in" +
            "   <foreach collection=\"ids\" separator=\",\" index=\"index\" item=\"item\" close=\")\" open=\"(\">" +
            "        #{item}" +
            "   </foreach>" +
            "</script>")
    int delete(@Param("ids") List<Integer> ids);

    /**
     * 查询所有数据
     * @return
     */
    @Select("select * from operator_role")
    List<OperatorRoleModel> selectAll();

    /**
     * 根据Id查找数据
     * @param id
     * @return
     */
    @Select("select * from operator_role where idx = #{id}")
    OperatorRoleModel selectById(@Param("id") Integer id);
}
