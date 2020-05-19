package com.group.foodservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.group.foodservice.exception.AccessDeniedException;
import com.group.foodservice.exception.BadRequestException;
import com.group.foodservice.model.Illness;
import com.group.foodservice.model.Taste;
import com.group.foodservice.model.User;
import com.group.foodservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册
     * @param data 前端的json数据
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/register")
    public String register(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user =  null;
        try{
            JSONObject jsonObject = JSONObject.parseObject(data);
            user = jsonObject.getObject("user", User.class);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        User res = userService.register(user);
        JSONObject resObject = new JSONObject();
        if(res != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", res);
            resObject.put("status", true);
//            resObject.put("username", res.getName());
//            response.sendRedirect("/");
        }else{
            resObject.put("status", false);
        }
        return resObject.toJSONString();
    }

    /**
     * 登录
     * @param data 前端的json数据
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/login")
    public String login(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(data);
            user = jsonObject.getObject("user", User.class);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        User res = userService.login(user.getName(), user.getPwd());
        JSONObject resObject = new JSONObject();
        if(res != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", res);
            resObject.put("status", true);

//            resObject.put("user", res);
//            response.sendRedirect("/");
        }else{
            resObject.put("status", false);
            resObject.put("msg", "用户名或密码错误");

        }
        return resObject.toString(SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 获取所有的口味和疾病，传给前端用于选择用户的偏好和疾病史
     * @return
     */
    @GetMapping("/getAllIllAndTaste")
    public String fillInfo(){
        List<Illness> illList = userService.getAllIllOptions();
        List<Taste> tasteList = userService.getAllTasteOptions();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("allIllness", illList);
        jsonObject.put("allTastes", tasteList);
        return jsonObject.toJSONString();
    }

    /**
     * 完善用户的偏好和疾病史
     * @param data 前端的json数据
     * @param request
     * @return
     */
    @PostMapping("/fillInfo")
    public String fillInfo(@RequestBody String data, HttpServletRequest request){
        List<Illness> illList;
        List<Taste> preferList;
        try {
            JSONObject jsonObject = JSONObject.parseObject(data);
            illList = jsonObject.getJSONArray("illHistory").toJavaList(Illness.class);
            preferList = jsonObject.getJSONArray("preference").toJavaList(Taste.class);
            if(illList == null || illList.size() == 0){
                throw new BadRequestException("illList is empty");
            }
            if(preferList == null || preferList.size() == 0){
                throw new BadRequestException("preferList is empty");
            }
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        HttpSession session = request.getSession();
        JSONObject resObject = new JSONObject();
        User userSession = (User) session.getAttribute("user");
        if(userSession == null){
//            resObject.put("status", false);
            throw new AccessDeniedException("Login first please");
        }else{
            boolean res = userService.updateInfoById(userSession.getId(), illList, preferList);
            resObject.put("status", res);
            userSession.setPrefer(preferList);
            userSession.setIllHistory(illList);
        }

        return resObject.toJSONString();
    }

    /**
     * 从session中拿用户的基本信息
     * @param request
     * @return
     */
    @GetMapping("/getInfo")
    public String getInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        JSONObject resObject = new JSONObject();
        User userSession = (User) session.getAttribute("user");
        if(userSession == null){
//            resObject.put("status", false);
            throw new AccessDeniedException("Login first please");
        }else{
            resObject.put("user", userSession);
            resObject.put("status", true);
        }

        return resObject.toString(SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 更改密码功能
     * @param data
     * @param request
     * @return
     */
    @PostMapping("/changePwd")
    public String changePwd(@RequestBody String data, HttpServletRequest request){
        User user = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(data);
            user = jsonObject.getObject("user", User.class);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        JSONObject resObject = new JSONObject();
        HttpSession session = request.getSession();
        User userSession = (User)session.getAttribute("user");

        if(userSession == null){
//            resObject.put("status", false);
            throw new AccessDeniedException("Login first please");
        }else {
            boolean res = userService.changePwd(userSession.getName(), user.getPwd());
            resObject.put("status", res);
        }
        return resObject.toJSONString();
    }
}
