package com.group.foodservice.service.serviceImpl;

import com.group.foodservice.exception.BadRequestException;
import com.group.foodservice.exception.ServerInternalException;
import com.group.foodservice.mapper.UserMapper;
import com.group.foodservice.model.Illness;
import com.group.foodservice.model.Taste;
import com.group.foodservice.model.User;
import com.group.foodservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated //service层要想使用valid功能，必须加上类注解@Validated
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户注册
     * @param user controller发来的用户信息，用@Valid注解作校验
     * @return 返回去掉密码的user对象
     */
    @Override
    public User register(@Valid User user) {
//        System.out.println(user);
        int i;
        try {
            i = userMapper.addUser(user);
        }catch (DataAccessException e){
            throw new BadRequestException("请正确填写信息");
        }
        catch (Exception e){
            throw new ServerInternalException("抱歉，出错了");
        }
        if(i != 0){
            user.setPwd("");
            return user;
        }else{
            return null;
        }
    }

    /**
     * 根据用户名和密码登录
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public User login(String name, String pwd) {
        User user1 = null;
        try{
            user1 = userMapper.queryUserByNamePwd(name, pwd);
        }catch (DataAccessException e){
            throw new BadRequestException("用户名或密码错误");
        }catch (Exception e){
            throw new ServerInternalException("抱歉，出错了");
        }
        if(user1 != null){
            user1.setPwd("");
        }
        return user1;
    }

    /**
     * 获取所有的疾病选项
     * @return
     */
    @Override
    public List<Illness> getAllIllOptions() {
        try{
            return userMapper.queryAllIllness();
        }catch (Exception e){
            throw new ServerInternalException("抱歉出错了");
        }
    }

    /**
     * 获取所有的口味选项
     * @return
     */
    @Override
    public List<Taste> getAllTasteOptions() {
        try{
            return userMapper.getAllTastes();
        }catch (Exception e){
            throw new ServerInternalException("抱歉出错了");
        }
    }

    /**
     * 根据id完善用户的偏好口味和疾病史
     * @param id 用户id
     * @param illList 疾病史
     * @param preferList 偏好口味
     * @return
     */
    @Override
    public boolean updateInfoById(int id, List<Illness> illList, List<Taste> preferList) {
//        System.out.println(id);
        int i;
        int i2;
        try {
            i = userMapper.fillPreference(id, preferList);
            i2 = userMapper.fillIllHistory(id, illList);
        }catch (Exception e){
            throw new ServerInternalException("抱歉出错了");
        }
        return i == preferList.size() && i2 == illList.size();
    }

    /**
     * 根据用户名更改密码
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public boolean changePwd(String name, String pwd) {
        if (pwd.matches("^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9~!@&%#_]{8,16}$")) {
            try {
                return userMapper.changePwd(name, pwd) == 1;
            }catch (Exception e){
                throw new ServerInternalException("抱歉出错了");
            }
        }else {
            throw new BadRequestException("密码必须由8到16位数字或字母组成，可以包含_、!、@、&、%、#");
        }
    }
}
