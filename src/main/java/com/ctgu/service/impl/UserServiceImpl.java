package com.ctgu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.dao.UserDao;
import com.ctgu.entity.User;
import com.ctgu.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;

	@Override
	public User getByUserId(Integer userId)
	{
		User user = userDao.getByUserId(userId);
		return user;
	}

}