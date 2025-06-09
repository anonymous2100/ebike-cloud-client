package com.ctgu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ctgu.constant.RedisConstant;
import com.ctgu.entity.User;
import com.ctgu.service.RedisService;
import com.ctgu.service.TokenService;

@Service("tokenService")
public class TokenServiceImpl implements TokenService
{
	@Autowired
	private RedisService redisService;

	@Override
	public void setUser(String token, User user)
	{
		/** 判断用户是否存在 */
		String userKey = RedisConstant.REDIS_ACCOUNT_USER + user.getUserId();
		String tokenOld = redisService.get(userKey);
		if (tokenOld != null)
		{
			// redisService.del(tokenOld);//可以同时登录
		}
		/** 新加token */
		String userString = JSON.toJSONString(user);
		redisService.add(token, userString, 30 * 24 * 3600);// 7天
		/** 用户和token对应关系 */
		redisService.add(userKey, token, 30 * 24 * 3600);
	}

	@Override
	public User getUser(String token)
	{
		String usetString = redisService.get(token);
		if (usetString != null)
		{
			User user = JSON.parseObject(usetString, User.class);
			if (user != null)
			{
				return user;
			}
		}
		return null;
	}

}