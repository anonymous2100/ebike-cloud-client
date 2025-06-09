package com.ctgu.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import com.ctgu.service.RedisService;

@Service("redisService")
public class RedisServiceImpl implements RedisService
{
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	@Qualifier("redisTemplate1")
	private RedisTemplate<String, String> redisTemplate1;

	@Override
	public void add(String key, String value)
	{

		ValueOperations<String, String> valueOperation = redisTemplate.opsForValue();
		valueOperation.set(key, value);
	}

	@Override
	public String get(String key)
	{
		ValueOperations<String, String> valueOperation = redisTemplate.opsForValue();
		return valueOperation.get(key);
	}

	@Override
	public String get(String hashId, String key)
	{
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		return hashOperations.get(hashId, key);
	}

	@Override
	public String getMain(String hashId, String key)
	{
		HashOperations<String, String, String> hashOperations = redisTemplate1.opsForHash();
		return hashOperations.get(hashId, key);
	}

	@Override
	public Map<String, String> getAll(String hashId)
	{
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		return hashOperations.entries(hashId);
	}

	@Override
	public void add(String key, String value, Integer seconds)
	{
		ValueOperations<String, String> valueOperation = redisTemplate.opsForValue();
		valueOperation.set(key, value, seconds, TimeUnit.SECONDS);

	}

	@Override
	public void del(String key)
	{
		redisTemplate.delete(key);
	}

	@Override
	public Long incr(String key, long liveTime)
	{
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		Long increment = entityIdCounter.getAndIncrement();

		if ((null == increment || increment.longValue() == 0) && liveTime > 0)
		{// 初始设置过期时间
			entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
		}
		return increment;
	}

	@Override
	public void addMain(String key, String value, Integer seconds)
	{
		ValueOperations<String, String> valueOperation = redisTemplate1.opsForValue();
		valueOperation.set(key, value, seconds, TimeUnit.SECONDS);
	}

	@Override
	public String getWay(String key)
	{
		ValueOperations<String, String> valueOperation = redisTemplate1.opsForValue();
		return valueOperation.get(key);
	}
}