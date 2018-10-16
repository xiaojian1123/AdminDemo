package com.zxj.agents.mapper;

import com.zxj.agents.model.OperateLogModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 操作员日志代理接口
 *
 * @author:zhongxiaojian
 * @date:2018-04-10 15:53
 */
@Mapper
public interface OperateLogMapper {
    /**
     * 插入操作员日志
     *
     * @param model
     * @return
     */
    @Insert("insert into operate_log" +
            "   (operator_id,utc,description)" +
            "       values" +
            "   (#{model.operatorId},unix_timestamp() * 1000,#{model.description})")
    int insert(@Param("model") OperateLogModel model);

    /**
     * 根据条件查询数据
     * @param operatorId
     * @param start
     * @param end
     * @param keyword
     * @return
     */
    @Select("<script>" +
            "select * from operate_log" +
            "        <where>" +
            "            <if test=\"null!= operatorId and ''!= operatorId\">" +
            "               and  operator_id = #{operatorId}" +
            "            </if>" +
            "            <if test=\"null!= start and null != end\">" +
            "                and utc BETWEEN #{start} and #{end}" +
            "            </if>" +
            "            <if test=\"null!=keyword and ''!=keyword\">" +
            "                and description like '%${keyword}%'" +
            "            </if>" +
            "        </where>" +
            "</script>")
    @Results({
            @Result(column = "operator_id",property = "operatorId"),

    })
    List<OperateLogModel> selectAll(@Param("operatorId") String operatorId,
                                    @Param("start") Long start,
                                    @Param("end") Long end,
                                    @Param("keyword") String keyword);
}
