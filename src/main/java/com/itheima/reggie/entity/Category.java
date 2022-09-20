package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类实体
 */
@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //类型 1：菜品分类；2：套餐分类
    private Integer type;
    //分类名称
    private String name;
    //顺序
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充字段
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充字段
    private String updateUser;
}
