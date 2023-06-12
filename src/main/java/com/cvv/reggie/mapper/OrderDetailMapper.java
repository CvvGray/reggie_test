package com.cvv.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cvv.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
     List<OrderDetail> selectOrderDetailByOrderId(Long orderId);
}