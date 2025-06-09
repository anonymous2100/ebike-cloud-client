package com.ctgu.service;

import java.util.Map;

/**
 * redis service 2017年2月24日 下午4:09:17
 */
public interface RedisService
{
	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 */
	void add(String key, String value);

	/**
	 * 设置有效期
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	void add(String key, String value, Integer seconds);

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 获取二级哈希
	 * 
	 * @param hashId
	 * @param key
	 * @return
	 */
	public String get(String hashId, String key);

	/**
	 * 获取二级哈希
	 * 
	 * @param hashId
	 * @param key
	 * @return
	 */
	public String getMain(String hashId, String key);

	/**
	 * 获取一级哈希
	 * 
	 * @param hashId
	 * @return
	 */
	public Map<String, String> getAll(String hashId);

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void del(String key);

	/**
	 * @Description: 获取自增长值
	 * @param key
	 *            key
	 * @return
	 */
	public Long incr(String key, long liveTime);

	void addMain(String key, String value, Integer seconds);

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	String getWay(String key);

}