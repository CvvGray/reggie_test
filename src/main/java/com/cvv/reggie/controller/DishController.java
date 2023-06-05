package com.cvv.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.common.R;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.entity.Category;
import com.cvv.reggie.entity.Dish;
import com.cvv.reggie.mapper.DishDtoMapper;
import com.cvv.reggie.service.CategoryService;
import com.cvv.reggie.service.DishFlavorService;
import com.cvv.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private DishDtoMapper dishDtoMapper;


    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> dish(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

//    /**
//     * 菜品信息分页查询
//     * @param page
//     * @param pageSize
//     * @param name
//     * @return
//     */
//    @GetMapping("/page")
//    public R<Page> dishPage(@Param("page") Integer page,@Param("pageSize") Integer pageSize,@Param("name") String name){
//        //构造分页构造器对象
//        Page<Dish> pageInfo = new Page<>(page,pageSize);
//        Page<DishDto> dishDtoPage = new Page<>();
//
//        //条件构造器
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        //添加过滤条件
//        queryWrapper.like(name != null,Dish::getName,name);
//        //添加排序条件
//        queryWrapper.orderByDesc(Dish::getUpdateTime);
//
//        //执行分页查询
//        dishService.page(pageInfo,queryWrapper);
//
//        //对象拷贝
//        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
//
//        List<Dish> records = pageInfo.getRecords();
//
//        List<DishDto> list = records.stream().map((item) -> {
//            DishDto dishDto = new DishDto();
//
//            BeanUtils.copyProperties(item,dishDto);
//
//            Long categoryId = item.getCategoryId();//分类id
//            //根据id查询分类对象
//            Category category = categoryService.getById(categoryId);
//
//            if(category != null){
//                String categoryName = category.getName();
//                dishDto.setCategoryName(categoryName);
//            }
//            return dishDto;
//        }).collect(Collectors.toList());
//
//        dishDtoPage.setRecords(list);
//
//
//        return R.success(dishDtoPage);
//    }


    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> dishPage(Integer page,Integer pageSize,String name){
        Page<DishDto> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = dishDtoMapper.selectDishdotPage(pageInfo, name);
        return R.success(dishDtoPage);
    }


    /**
     * 获取菜品数据，并回显给前端
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> viewDish(@PathVariable Long id){
        DishDto dishDto = dishService.getDishdtoById(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> modifyDish(@RequestBody DishDto dishDto){
        dishService.updateDishdto(dishDto);
        return R.success("修改成功");
    }

    /**
     * 启售/停售
     * @param changedStatus
     * @param id
     * @return
     */
    @PostMapping("/status/{changedStatus}")
    public R<String> modifyDishStatus(@PathVariable Integer changedStatus,@RequestParam(name = "ids") Long id){

//        Dish stopSellDish = dishService.getById(id);
//        stopSellDish.setStatus(changedStatus);
//        dishService.updateById(stopSellDish);
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Dish::getId,id)
                    .set(Dish::getStatus,changedStatus);
        dishService.update(null,updateWrapper);

        return R.success("修改成功");
    }


    @DeleteMapping
    public R<String> deleteDish(@RequestParam(name = "ids") Long id){
        dishService.removeWithFlavor(id);
        return R.success("删除成功");
    }


}
