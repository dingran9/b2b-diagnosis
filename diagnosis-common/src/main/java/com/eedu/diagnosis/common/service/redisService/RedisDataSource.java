package com.eedu.diagnosis.common.service.redisService;

import redis.clients.jedis.ShardedJedis;

/**
 * Author: dingran
 * Date: 2015/10/29
 * Description:Redis服务
 */
public interface RedisDataSource {

    /**
     * 取得redis的客户端
     * @return
     */
    public abstract ShardedJedis getRedisClient();

    /**
     * 将资源返还给pool
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);

    /**
     *出现异常后，将资源返还给pool
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken);


}