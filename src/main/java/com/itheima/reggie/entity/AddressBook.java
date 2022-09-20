package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地址簿
 */
@Data
public class AddressBook implements Serializable {
    private static final long serialVersionUID = 1L;
    //id
    private Long id;
    //用户id
    private Long userId;
    //收货人
    private String consignee;
    //性别
    private Integer sex;
    //手机号
    private String phone;
    //省级区划编号
    private String provinceCode;
    //省级名称
    private String provinceName;
    //市级区划编号
    private String cityCode;
    //市级名称
    private String cityName;
    //区级区划编号
    private String districtCode;
    //区级mingc
    private String districtName;
    //详细地址
    private String detail;
    //标签
    private String label;
    //是否默认 0：否  1：是
    private Integer isDefault;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充字段
    private LocalDateTime updateTime;
}
