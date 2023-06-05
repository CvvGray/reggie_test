package com.cvv.reggie;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvv.reggie.dto.DishDto;
import com.cvv.reggie.mapper.DishDtoMapper;
import com.cvv.reggie.mapper.DishMapper;
import com.cvv.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class SpringBootTestReggie {

    @Resource
    private DishDtoMapper dishDtoMapper;

    @Resource
    private DishService dishService;

    @Test
    public void test01(){
        DishDto dishDto = dishDtoMapper.selectDishdtoById(1397850140982161409L);
    }

    @Test
    public void test02(){
        DishDto dishDto = dishService.getDishdtoById(1397850140982161409L);
        System.out.println(dishDto.getFlavors());
    }
}
