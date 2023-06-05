package com.cvv.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cvv.reggie.utils.ThreadLocalForCurrentUserId;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long currentId = ThreadLocalForCurrentUserId.getCurrentId();
        metaObject.setValue("createUser", currentId);
        metaObject.setValue("updateUser", currentId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", ThreadLocalForCurrentUserId.getCurrentId());
    }
}
