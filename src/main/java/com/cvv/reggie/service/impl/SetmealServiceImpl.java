package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.dto.SetmealDto;
import com.cvv.reggie.entity.Setmeal;
import com.cvv.reggie.entity.SetmealDish;
import com.cvv.reggie.mapper.SetmealMapper;
import com.cvv.reggie.service.SetmealDishService;
import com.cvv.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.print.DialogOnTop;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    private SetmealMapper setmealMapper;

    @Resource
    private SetmealDishService setmealDishService;

    /**
     * 分页查询
     * @param page
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDto> getAllSetmealWithCategoryName(Page<Setmeal> page, @Param("name") String name) {

        return setmealMapper.selectSetmealDtoWithCategoryName(page,name);
    }

    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }


    @Override
    public SetmealDto getSetmealDtobyId(Long id) {
        SetmealDto setmealDto = setmealMapper.selectSetmealDtobyId(id);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDto.setSetmealDishes(setmealDishService.list(queryWrapper));

        return setmealDto;
    }

    @Override
    public void updateSetmeal(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void removeWithDishes(Long id) {
        this.removeById(id);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);
    }
}
