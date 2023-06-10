package com.cvv.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cvv.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    List<DishFlavor> selectAllByDishIdDishFlavorList(Long dishId);
}
