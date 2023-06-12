package com.cvv.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.common.R;
import com.cvv.reggie.dto.SetmealDto;
import com.cvv.reggie.entity.Dish;
import com.cvv.reggie.entity.Setmeal;
import com.cvv.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetMealController {

    @Resource
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }



    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> setmealPage(Integer page,Integer pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        setmealService.getAllSetmealWithCategoryName(pageInfo,name);
        return R.success(pageInfo);
    }


    /**
     * 获取菜品数据，并回显给前端
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> viewSetmeal(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getSetmealDtobyId(id);
        return R.success(setmealDto);
    }

    /**
     * 修改套餐信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> modifySetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.updateSetmeal(setmealDto);
        return R.success("修改成功");
    }


    /**
     * 启售/停售，批量启售/停售
     * @param changedStatus
     * @param ids
     * @return
     */
    @PostMapping("/status/{changedStatus}")
    public R<String> modifyDishStatus(@PathVariable Integer changedStatus,@RequestParam(name = "ids") Long ...ids){
        for (Long id : ids) {
            LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Setmeal::getId,id)
                    .set(Setmeal::getStatus,changedStatus);
            setmealService.update(null,updateWrapper);
        }
        return R.success("修改成功");
    }


    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    //allEntries = true表示当我们删除一条套餐数据时，清空setmealCache下的所有缓存数据
    public R<String> deleteSetmeal(@RequestParam(name = "ids") Long ...ids){
        for (Long id : ids) {
            setmealService.removeWithDishes(id);
        }
        return R.success("删除成功");
    }



    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> listSetmeal(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId())
                    .eq(Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

}
