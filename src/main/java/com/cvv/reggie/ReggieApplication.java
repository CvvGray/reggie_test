package com.cvv.reggie;

import com.cvv.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@SpringBootApplication
@Slf4j
@ServletComponentScan
@EnableCaching
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
    }

}
