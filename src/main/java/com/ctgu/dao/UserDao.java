package com.ctgu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ctgu.entity.User;

/**
 */
public interface UserDao
{
	/**
	 * 添加
	 * 
	 * @param user
	 */
	void insert(User user);

	/**
	 * 用户
	 * 
	 * @param params
	 * @return
	 */
	User getByAccountIdAndPhone(Map<String, Object> params);

	/**
	 * 根据用户id查询
	 * 
	 * @param userId
	 * @return
	 */
	User getByUserId(Integer userId);

	/**
	 * 更新
	 * 
	 * @param user
	 */
	void update(User user);

	/**
	 * 更新押金支付状态
	 * 
	 * @param params
	 */
	void updateDeposit(Map<String, Object> params);

	/**
	 * 更新账户金额
	 * 
	 * @param params
	 */
	void updateMoney(Map<String, Object> params);

	/**
	 * 更新押金金额
	 * 
	 * @param params
	 */
	void updateDepositMoney(Map<String, Object> params);

	/**
	 * 实名认证
	 * 
	 * @param params
	 */
	void updateNameAuth(Map<String, Object> params);

	/**
	 * 更新免押会员到期日期
	 * 
	 * @param params
	 */
	void updateDepositDate(Map<String, Object> params);

	/**
	 * 导入别的平台数据
	 * 
	 * @param user
	 */
	void insertOther(User user);

	List<User> getByNO(@Param(value = "idNO") String idNO);

	List<User> getByIdNOAndId(Map<String, Object> params);

	void editSex(Integer userId);
}