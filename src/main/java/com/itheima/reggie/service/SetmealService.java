package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关系关联
     *
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐
     *
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 根据id查询套餐信息和对应的菜品信息
     *
     * @param id
     * @return
     */
    SetmealDto getByIdWithFlavor(Long id);

    /**
     * 修改套餐
     *
     * @param setmealDto
     */
    void updaeWithFlavor(SetmealDto setmealDto);
}
