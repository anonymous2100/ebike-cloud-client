package com.ctgu.service;

import com.ctgu.entity.User;

/**
 * token service
 * 
 * @author Leon 2017年2月24日 下午4:09:17
 */
public interface TokenService
{
	/**
	 * 设置用户
	 * 
	 * @param user
	 */
	void setUser(String token, User user);

	/**
	 * 获取用户
	 * 
	 * @param token
	 * @return
	 */
	User getUser(String token);

}