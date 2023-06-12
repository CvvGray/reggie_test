package com.cvv.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.common.R;
import com.cvv.reggie.dto.OrdersDto;
import com.cvv.reggie.entity.Orders;
import com.cvv.reggie.service.OrderService;
import com.cvv.reggie.utils.ThreadLocalForCurrentUserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page<OrdersDto>> userOrderPage(Integer page,Integer pageSize){
        Page<OrdersDto> pageInfo = new Page<>(page,pageSize);
        Long currentId = ThreadLocalForCurrentUserId.getCurrentId();

        Page<OrdersDto> ordersDtoPage = orderService.getOrdersDtoPageByUserId(pageInfo, currentId);
        return R.success(ordersDtoPage);
    }



    @GetMapping("/page")
    public R<Page<Orders>> orderPage(Integer page, Integer pageSize, Long number, String beginTime,String endTime){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        if (number != null){
            queryWrapper.eq(Orders::getNumber,number);
        }

        if (StringUtils.isNotBlank(beginTime)){
            LocalDateTime LocalBeginTime = LocalDateTime.parse(beginTime,dateTimeFormatter);
            queryWrapper.gt(Orders::getOrderTime,LocalBeginTime);
        }

        if (StringUtils.isNotBlank(endTime)){
            LocalDateTime LocalEndTime = LocalDateTime.parse(endTime,dateTimeFormatter);
            queryWrapper.lt(Orders::getOrderTime,LocalEndTime);
        }

        Page<Orders> pageData = orderService.page(pageInfo, queryWrapper);


        return R.success(pageData);
    }

}