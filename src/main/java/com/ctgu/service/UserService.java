package com.ctgu.service;

import com.ctgu.entity.User;

/**
 * 账户service 2017年2月24日 下午4:09:17
 */
public interface UserService
{
	/**
	 * 根据用户id查询
	 * 
	 * @param userId
	 * @return
	 */
	User getByUserId(Integer userId);
}