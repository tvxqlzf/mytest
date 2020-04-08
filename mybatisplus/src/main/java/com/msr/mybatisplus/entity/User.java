package com.msr.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    //属性 ——  字段
    private Long id;
    private String name;
    private Integer age;
    private String email;
    //自动填充 需要在属性上通过注解来描述

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    //生成 get  和 set  方法
    //生成构造方法
    //lombok
    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

}
