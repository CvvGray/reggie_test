package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.entity.Dish;
import com.cvv.reggie.entity.DishFlavor;
import com.cvv.reggie.entity.Employee;
import com.cvv.reggie.mapper.DishDtoMapper;
import com.cvv.reggie.mapper.DishMapper;
import com.cvv.reggie.service.DishFlavorService;
import com.cvv.reggie.service.DishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{
    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private DishDtoMapper dishDtoMapper;

    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors.forEach((flavor) ->{
            System.out.println("-------------------" + flavor.getDishId());
        });

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public Page<DishDto> getDishdotPage(Page<DishDto> page, String name) {
        return dishDtoMapper.selectDishdotPage(page,name);
    }


    @Override
    public DishDto getDishdtoById(Long id) {
        DishDto dishDto = dishDtoMapper.selectDishdtoById(id);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void updateDishdto(DishDto dishDto) {
        this.updateById(dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void removeWithFlavor(Long id) {
        this.removeById(id);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        dishFlavorService.remove(queryWrapper);
    }
}
