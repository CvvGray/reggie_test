package com.cvv.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cvv.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
