package com.ctgu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ctgu.entity.BorrowCheckConfigLog;

/**
 */
@Mapper
public interface BorrowCheckConfigLogMapper
{
	int deleteByPrimaryKey(Integer id);

	int insertSelective(BorrowCheckConfigLog row);

	BorrowCheckConfigLog selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BorrowCheckConfigLog row);

	BorrowCheckConfigLog findByNameAndSerialNO(String handlerName, String serialNO);

	void updateFailedReason(@Param("serialNO") String serialNO, @Param("failedReason") String failedReason);
}
