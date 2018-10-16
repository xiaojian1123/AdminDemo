package com.zxj.agents.operator.mapper;

import com.zxj.agents.operator.model.OperatorModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 操作员代理
 *
 * @author:zhongxiaojian
 * @date:2018-04-09 16:33
 */
@Mapper
public interface OperatorMapper {

    /**
     * 添加或更新
     *
     * @param model
     * @return
     */
    @Insert("insert into operators" +
            "        (id,role_id,password,phone,addr,login_utc,creater)" +
            "        values" +
            "        (#{model.id},#{model.roleId},#{model.password},#{model.phone},#{model.addr},#{model.loginUtc},#{model.creater}" +
            "        ) ON DUPLICATE KEY UPDATE" +
            "        role_id = #{model.roleId}," +
            "        phone = #{model.phone}," +
            "        addr = #{model.addr}")
    int insertOrUpdate(@Param("model") OperatorModel model);

    /**
     * 根据id删除
     *
     * @param ids 操作员id集合。
     * @return
     */
    @Delete("delete from operators where id = #{id}")
    int delete(@Param("id") String ids);


    /**
     * 根据id查找数据
     *
     * @param id
     * @return
     */
    @Select("select * from operators where id = #{id}")
    @Results({
            @Result(column = "role_id",property = "roleId"),
            @Result(column = "login_utc",property = "loginUtc")
    })
    OperatorModel selectById(@Param("id") String id);

    /**
     * 根据用户ID更改登录时间
     * @param account
     * @return
     */
    @Update("UPDATE operators" +
            "        SET login_utc = unix_timestamp() * 1000" +
            "        WHERE id = #{id}")
    int updateLoginUtcById(@Param("id") String account);

    /**
     * 根据用户id修改用户密码
     * @param id
     * @param newPassword
     * @return
     */
    @Update("UPDATE operators" +
            "        SET `password` = #{password}" +
            "        WHERE id = #{id}")
    int updatePasswordById(@Param("id") String id, @Param("password") String newPassword);

    /**
     * 根据CreatorId查找数据
     * @param id
     * @return
     */
    @Select("select a.*,b.name as roleName" +
            "        from" +
            "        operators a INNER JOIN operator_role b" +
            "        where a.role_id= b.idx and a.creater = #{creater};")
    @Results({
            @Result(column = "role_id",property = "roleId"),
            @Result(column = "login_utc",property = "loginUtc")
    })
    List<OperatorModel> selectByCreatorId(@Param("creater") String id);

}
