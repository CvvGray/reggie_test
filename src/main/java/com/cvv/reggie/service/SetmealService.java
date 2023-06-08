package com.cvv.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.dto.SetmealDto;
import com.cvv.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;


public interface SetmealService extends IService<Setmeal> {
    Page<SetmealDto> getAllSetmealWithCategoryName(Page<Setmeal> page, @Param("name") String name);

    @Transactional
    void saveWithDish(SetmealDto setmealDto);

    SetmealDto getSetmealDtobyId(Long id);

    void updateSetmeal(SetmealDto setmealDto);

    void removeWithDishes(Long id);
}
