package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品口味
 */
@Data
public class DishFlavor implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //菜品id
    private Long dishId;
    //口味名称
    private String name;
    //口味数据list
    private String value;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充字段
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充字段
    private String updateUser;

    //是否删除
    private Integer isDeleted;
}
