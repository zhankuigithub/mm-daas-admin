package com.mm.system.service;


/**
 * <p>
 * redis操作类
 * </p>
 *
 * @author
 * @date 2023-04-24
 */
public interface IRedisValueGetter<T> {

    /**
     * <p> 获取val </p>
     * T
     *
     * @return T
     **/
    T getValueIfCacheMiss() throws Exception;
}
