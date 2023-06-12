package com.cvv.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cvv.reggie.dto.OrdersDto;
import com.cvv.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户查看订单信息
     * @param pageInfo
     * @param userId
     * @return
     */
     Page<OrdersDto> getOrdersDtoPageByUserId(Page<OrdersDto> pageInfo, Long userId);

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);
}
