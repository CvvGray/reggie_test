package com.cvv.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    Page<DishDto> getDishdotPage(Page<DishDto> page,String name);

    DishDto getDishdtoById(Long id);

    void updateDishdto(DishDto dishDto);

    void removeWithFlavor(Long id);
}
