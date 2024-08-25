package com.mm.system.service;



public interface IRedisValueGetter<T> {

    /**
     * <p> 获取val </p>
     * T
     *
     * @return T
     **/
    T getValueIfCacheMiss() throws Exception;
}
