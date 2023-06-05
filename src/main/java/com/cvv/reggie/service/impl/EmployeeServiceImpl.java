package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.entity.Employee;
import com.cvv.reggie.mapper.EmployeeMapper;
import com.cvv.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
