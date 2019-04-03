package com.eureka.client.utils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * RedisUtils
 *
 * @DESCREPTION:
 * @Author: zhanghaojie
 * @Date: 2018/12/28上午11:34
 */
@Slf4j
public class RedisUtils {

    private static void destroyJedis(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.quit();
            }catch (Exception e) {
                log.info("RedisUtil-jedis.quit()");
                e.printStackTrace();
            }

            try {
                jedis.disconnect();
            }catch (Exception e) {
                log.info("RedisUtil-jedis.quit()");
                e.printStackTrace();
            }

        }
    }

    // 释放资源
    public synchronized static void returnResource(JedisPool jedisPool, Jedis jedis){
        if (jedis != null) {
            try{
                jedisPool.returnResource(jedis);
            }catch (Exception e){
                log.info("缓存服务释放连接发送异常：" );
                e.printStackTrace();
                destroyJedis(jedis);
            }

        }
    }

    // 出现异常释放资源
    public synchronized static void returnBrokenResource(JedisPool jedisPool, Jedis jedis) {

        if (jedis != null) {
            try{
                jedisPool.returnBrokenResource(jedis);
            }catch (Exception e){
                log.info("缓存服务释放连接发送异常：" );
                e.printStackTrace();
                destroyJedis(jedis);

            }

        }
    }

}
