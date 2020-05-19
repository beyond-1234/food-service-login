package com.group.foodservice.mapper;

import com.group.foodservice.model.Illness;
import com.group.foodservice.model.Taste;
import com.group.foodservice.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    /**
     * 添加用户，用于用户注册
     * @param user 用户信息，@Options注解会将自增主键自动放入user对象的id属性
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into User(name, gender, birth, laborStrength, email, weight, height, pwd, imgBase64)" +
            "value(#{name}, #{gender}, #{birth}, #{laborStrength}, #{email}, #{weight}, #{height}, #{pwd}, #{imgBase64})")
    int addUser(User user);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    @Select("select * from User where id = #{id}")
    User queryUserById(int id);

    /**
     * 根据用户名密码查询用户信息，包括偏好口味和疾病史，用于登录
     * @param name 用户名
     * @param pwd 密码
     * @return User对象
     */
    @Select("select * from User where name = #{name} and pwd = #{pwd}")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id",property = "prefer", many = @Many(select = "com.group.foodservice.mapper.UserMapper.queryPreferenceById")),
            @Result(column = "id",property = "illHistory", many = @Many(select = "com.group.foodservice.mapper.UserMapper.queryIllHistoryById"))
    })
    User queryUserByNamePwd(String name, String pwd);

    /**
     * 根据用户id查询疾病史
     * @param id
     * @return
     */
    @Select("select * from Illness where id in (select item from IllHistory where id = #{id})")
    List<Illness> queryIllHistoryById(int id);

    /**
     * 根据用户id查询偏好口味
     * @param id
     * @return
     */
    @Select("select * from Taste where id in (select item from Preference where id = #{id})")
    List<Taste> queryPreferenceById(int id);

    /**
     * 根据用户id完善用户偏好口味，使用xml配置
     * @param id
     * @param preferList
     * @return
     */
    int fillPreference(int id, List<Taste> preferList);

    /**
     * 根据用户id完善用户疾病史
     * @param id
     * @param illList
     * @return
     */
    int fillIllHistory(int id, List<Illness> illList);

    /**
     * 查询所有疾病选项
     * @return
     */
    @Select("select * from Illness")
    List<Illness> queryAllIllness();

    /**
     * 查询所有口味选项
     * @return
     */
    @Select("select * from Taste")
    List<Taste> getAllTastes();

    /**
     * 根据用户名改密码
     * @param name
     * @param pwd
     * @return
     */
    @Update("update User set pwd = #{pwd} where name = #{name}")
    int changePwd(String name, String pwd);

}
