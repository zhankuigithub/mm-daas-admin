package com.mm.system.service;


public interface IRedisRepository {

    /**
     * <p> key是否存在 </p>
     *
     * @param key boolean
     * @return boolean
     **/
    boolean isExist(String key);

    /**
     * <p> setIfAbsent </p>
     *
     * @param key
     * @param obj
     * @param expire boolean
     * @return boolean
     **/
    boolean setIfAbsent(String key, Object obj, Long expire);

    /**
     * <p> set </p>
     *
     * @param key
     * @param obj
     * @param expire boolean
     * @return boolean
     **/
    boolean set(String key, Object obj, Long expire);

    /**
     * <p> delete </p>
     *
     * @param key boolean
     * @return boolean
     **/
    boolean delete(String key);

    /**
     * <p> get </p>
     *
     * @param key T
     * @return T
     **/
    <T> T get(String key);

    /**
     * <p> get 不加锁 </p>
     *
     * @param key
     * @param expire
     * @param var3   T
     * @return T
     **/
    <T> T unblockingGet(String key, Long expire, IRedisValueGetter<T> var3) throws Exception;

    /**
     * <p> get 加锁 </p>
     *
     * @param key
     * @param expire
     * @param var3   T
     * @return T
     **/
    <T> T blockingGet(String key, Long expire, IRedisValueGetter<T> var3) throws Exception;
}
