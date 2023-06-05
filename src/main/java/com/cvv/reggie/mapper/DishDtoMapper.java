package com.cvv.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.dto.DishDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DishDtoMapper{
    Page<DishDto> selectDishdotPage(Page<DishDto> page, @Param("name") String name);

    DishDto selectDishdtoById(Long id);

}
