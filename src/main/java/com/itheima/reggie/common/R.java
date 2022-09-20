package com.itheima.reggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 *
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code;//200成功，-1未登录，0和其他数字为失败
    private String msg;//错误信息
    private T data;//数据
    private Map map = new HashMap();//动态数据

    public static <T> R<T> success(T object, String msg) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;
        r.msg = msg;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public static <T> R<T> error(String msg, int code) {
        R r = new R();
        r.msg = msg;
        r.code = code;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}