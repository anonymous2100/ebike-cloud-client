package com.ctgu.service;

import javax.servlet.http.HttpServletRequest;

import com.ctgu.entity.Machine;
import com.ctgu.entity.User;
import com.ctgu.pojo.BorrowBusMoney;
import com.ctgu.vo.input.BorrowBikeVO;

/**
 * 设备service
 */
public interface MachineService
{

	/**
	 * 借车旧接口
	 *
	 * @param serNO
	 * @param userId
	 * @param accountId
	 * @param machineNO
	 * @param ble
	 * @return
	 */
	BorrowBusMoney borrowBike(String serNO, User user, Integer accountId, Machine machine, Boolean ble,
			Integer orderSource);

	/**
	 * 重构之后的借车接口
	 *
	 * @param serNO
	 * @param userId
	 * @param accountId
	 * @param machineNO
	 * @param ble
	 * @return
	 */
	void borrowBikeNew(HttpServletRequest request, BorrowBikeVO vo, String serNO);

	Machine getByUserCode(String userCode);

}