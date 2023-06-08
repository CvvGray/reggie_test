package com.cvv.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.common.R;
import com.cvv.reggie.entity.Category;
import com.cvv.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 新增菜品/套餐
     * @param category
     * @return
     */
    @PostMapping
    public R<String> saveCategory(@RequestBody Category category){
        log.info(category.toString());
        categoryService.save(category);
        return R.success("菜品添加成功");
    }

    /**
     * 菜品/套餐分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> getCategoryForPage(Integer page,Integer pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 删除菜品分类/套餐分类
     */
    @DeleteMapping
    public R<String> removeCategory(Long ids){
        log.info("接收到的id为" + ids);
        if (ids == null){
            return R.error("未知错误");
        }
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 修改菜单分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 菜品类型
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List> listR(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getType,category.getType())
                    .orderByAsc(Category::getSort)
                    .orderByAsc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }




}
