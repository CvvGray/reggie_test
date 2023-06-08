package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.entity.User;
import com.cvv.reggie.mapper.UserMapper;
import com.cvv.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
