package com.cvv.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.common.R;
import com.cvv.reggie.entity.Employee;
import com.cvv.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.jni.Local;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource()
    private EmployeeService employeeService;

    /**
     *
     * @param request
     * @param employeeFromBrowser 从前端发送过来的ajax数据会被封装奥此对象中
     * @return 返回登录失败或者登录成功的信息和数据
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employeeFromBrowser){
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername,employeeFromBrowser.getUsername());
        Employee employeeFromDB = employeeService.getOne(employeeLambdaQueryWrapper);

        if (employeeFromDB == null){
            return R.error("登录失败，此用户不存在");
        }

        String md5Password = DigestUtils.md5DigestAsHex(employeeFromBrowser.getPassword().getBytes());

        if(!employeeFromDB.getPassword().equals(md5Password)){
            return R.error("登陆失败，密码错误");
        }
        if (employeeFromDB.getStatus() == 0){
            return R.error("此账号已被禁用");
        }

        request.getSession().setAttribute("employee",employeeFromDB.getId());

        return R.success(employeeFromDB);
    }

    /**
     * 退出登陆的controller
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工方法
     * @param request
     * @param employeeFromBrowser
     * @return
     */
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request,@RequestBody Employee employeeFromBrowser){

        employeeFromBrowser.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employeeFromBrowser.setCreateTime(LocalDateTime.now());
//        employeeFromBrowser.setUpdateTime(LocalDateTime.now());
//
//        Long createUserID = (Long) request.getSession().getAttribute("employee");
//        employeeFromBrowser.setCreateUser(createUserID);
//        employeeFromBrowser.setUpdateUser(createUserID);

        employeeService.save(employeeFromBrowser);

        return R.success("添加成功");

    }


    /**
     * 员工分页查询
     * @param page      页码
     * @param pageSize  每页显示条数
     * @param name      模糊查询的条件
     * @return    Page对象
     */

    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize,String name){
        //实例化分页构造器
        Page pageInfo = new Page(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name)
                    .orderByAsc(Employee::getCreateTime);

        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employeeFromBrowser
     * @return
     */
    @PutMapping
    public R<String> updateEmployee(HttpServletRequest request,@RequestBody Employee employeeFromBrowser){
        //employeeFromBrowser.setUpdateTime(LocalDateTime.now());
        //employeeFromBrowser.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employeeFromBrowser);
        return R.success("修改成功");
    }


    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id){
        log.info("正在根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }

}
