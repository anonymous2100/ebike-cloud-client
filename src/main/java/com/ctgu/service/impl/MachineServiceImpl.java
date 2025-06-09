package com.ctgu.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ctgu.entity.Machine;
import com.ctgu.entity.User;
import com.ctgu.pojo.BorrowBusMoney;
import com.ctgu.service.MachineService;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MachineServiceImpl implements MachineService
{
	@Override
	public void borrowBikeNew(HttpServletRequest request, BorrowBikeVO vo, String serNO)
	{
		// 省略业务代码
		// ...
	}

	@Override
	public BorrowBusMoney borrowBike(String serNO, User user, Integer accountId, Machine machine, Boolean ble,
			Integer orderSource)
	{
		// 省略业务代码
		// ...
		return null;
	}

	@Override
	public Machine getByUserCode(String userCode)
	{
		// 省略业务代码
		// ...
		return null;
	}
}
