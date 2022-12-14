package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    //修改菜品，同时修改菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void updaeWithFlavor(DishDto dishDto);

    /**
     * 删除菜品
     *
     * @param ids
     */
    void removeWithDish(List<Long> ids);
}
