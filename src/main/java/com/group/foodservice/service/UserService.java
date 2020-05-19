package com.group.foodservice.service;

import com.group.foodservice.model.Illness;
import com.group.foodservice.model.Taste;
import com.group.foodservice.model.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public interface UserService {
    /**
     * 用户注册
     * @param user controller发来的用户信息，用@Valid注解作校验
     * @return 返回去掉密码的user对象
     */
    User register(@Valid User user);
    /**
     * 根据用户名和密码登录
     * @param name
     * @param pwd
     * @return
     */
    User login(String name, String pwd);
    /**
     * 获取所有的疾病选项
     * @return
     */
    List<Illness> getAllIllOptions();
    /**
     * 获取所有的口味选项
     * @return
     */
    List<Taste> getAllTasteOptions();
    /**
     * 根据id完善用户的偏好口味和疾病史
     * @param id 用户id
     * @param illList 疾病史
     * @param preferList 偏好口味
     * @return
     */
    boolean updateInfoById(int id, List<Illness> illList, List<Taste> preferList);
    /**
     * 根据用户名更改密码
     * @param name
     * @param pwd
     * @return
     */
    boolean changePwd(String name, String pwd);
}
