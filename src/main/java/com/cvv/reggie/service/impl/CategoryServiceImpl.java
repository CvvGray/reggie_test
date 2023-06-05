package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.common.R;
import com.cvv.reggie.entity.Category;
import com.cvv.reggie.entity.Dish;
import com.cvv.reggie.entity.Setmeal;
import com.cvv.reggie.exception.CustomException;
import com.cvv.reggie.mapper.CategoryMapper;
import com.cvv.reggie.service.CategoryService;
import com.cvv.reggie.service.DishService;
import com.cvv.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private DishService dishService;

    @Resource
    private SetmealService setmealService;
    @Override
    public void remove(Long id) throws RuntimeException{
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int countDish = dishService.count(dishLambdaQueryWrapper);

        if (countDish > 0 ){
            throw  new CustomException("此分类中含有菜单");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int countSetmeal = setmealService.count(setmealLambdaQueryWrapper);
        if (countSetmeal > 0 ){
            throw  new CustomException("此分类中含有套餐");
        }
        super.removeById(id);
    }
}
