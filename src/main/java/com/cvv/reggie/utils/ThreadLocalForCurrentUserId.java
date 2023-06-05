package com.cvv.reggie.utils;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:基于ThreadLocal封装的工具类，用户保存和获取当前登录用户id
 */

public class ThreadLocalForCurrentUserId {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
