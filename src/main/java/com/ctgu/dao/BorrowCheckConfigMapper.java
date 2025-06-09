package com.ctgu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ctgu.entity.BorrowCheckConfig;

@Mapper
public interface BorrowCheckConfigMapper
{
	List<BorrowCheckConfig> selectAll();

	List<BorrowCheckConfig> selectAllByType(@Param("type") String type);

}
