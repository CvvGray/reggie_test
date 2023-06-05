package com.cvv.reggie.common;

import com.cvv.reggie.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
        /**
         * 处理表中数据已存在的异常
         * @param ex
         * @return
         */
        @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
        public R<String> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex){
                log.error(ex.getMessage());
                if(ex.getMessage().contains("Duplicate entry")){
                        String[] strings = ex.getMessage().split(" ");
                        return R.error(strings[2] + "已存在");
                }

                return R.error("未知错误");
        }

        /**
         * 处理业务异常
         * @param ex
         * @return
         */
        @ExceptionHandler(CustomException.class)
        public R<String> CustomExceptionHandler(CustomException ex){
                log.error(ex.getMessage());
                return R.error(ex.getMessage());
        }

}
