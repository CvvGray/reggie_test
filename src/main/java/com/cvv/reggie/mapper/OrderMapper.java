package com.cvv.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.dto.OrdersDto;
import com.cvv.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    Page<OrdersDto> selectOrdersDtoPageByUserId(Page<OrdersDto> pageInfo, Long userId);
}