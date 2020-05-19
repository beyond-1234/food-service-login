package com.group.foodservice.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class User {
    private int id;
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9~!@&%#_]{8,16}$")//必须包含一个大写，一个小写字母，且长度为8到16位
    private String pwd;
    @NotNull(message = "昵称不能为空")
    @NotBlank(message = "昵称不能为空")
    private String name;
    @Min(value = 0, message = "性别只能为男或女")
    @Max(value = 1, message = "性别只能为男或女")
    private int gender;
    @NotNull(message = "出生日期不能为空")
    @Past(message = "出生日期只能是过去的时间")
    @JSONField(serialzeFeatures = SerializerFeature.WriteDateUseDateFormat)
    private Date birth;
    @NotNull(message = "头像不能为空")
    @NotBlank(message = "头像不能为空")
    @NotEmpty(message = "头像不能为空")
    private String imgBase64;
    @Min(value = 0, message = "劳动强度只能是轻、中、强")
    @Max(value = 2, message = "劳动强度只能是轻、中、强")
    private int laborStrength;
    @NotNull(message = "邮箱不能为空")
    @NotBlank(message = "邮箱不能为空")
    @NotEmpty(message = "邮箱不能为空")
    private String email;
    @Min(value = 0, message = "输入的体重无效")
    @Max(value = 450, message = "输入的体重无效")
    private int weight;
    @Min(value = 0, message = "输入的身高无效")
    @Max(value = 300, message = "输入的身高无效")
    private int height;

    private List<Taste> prefer;
    private List<Illness> illHistory;

    public User() {
        super();
    }

    public User(int id, String pwd, String name, int gender, Date birth, String imgBase64,  int laborStrength,  String email,  int weight, int height, List<Taste> perfer, List<Illness> illHistory) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.imgBase64 = imgBase64;
        this.laborStrength = laborStrength;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.prefer = perfer;
        this.illHistory = illHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getLaborStrength() {
        return laborStrength;
    }

    public void setLaborStrength(int laborStrength) {
        this.laborStrength = laborStrength;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Taste> getPrefer() {
        return prefer;
    }

    public void setPrefer(List<Taste> prefer) {
        this.prefer = prefer;
    }

    public List<Illness> getIllHistory() {
        return illHistory;
    }

    public void setIllHistory(List<Illness> illHistory) {
        this.illHistory = illHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", birth=" + birth +
                ", imgBase64='" + imgBase64 + '\'' +
                ", laborStrength=" + laborStrength +
                ", email='" + email + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", prefer=" + prefer +
                ", illHistory=" + illHistory +
                '}';
    }
}
