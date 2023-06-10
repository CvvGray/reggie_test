package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.entity.ShoppingCart;
import com.cvv.reggie.mapper.ShoppingCartMapper;
import com.cvv.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
