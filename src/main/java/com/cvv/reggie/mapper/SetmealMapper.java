package com.cvv.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.dto.SetmealDto;
import com.cvv.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    Page<SetmealDto> selectSetmealDtoWithCategoryName(Page<Setmeal> page, @Param("name") String name);

    SetmealDto selectSetmealDtobyId(Long id);
}
