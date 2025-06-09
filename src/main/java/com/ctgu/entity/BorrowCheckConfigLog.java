package com.ctgu.entity;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 处理器处理日志记录实体类
 */
@TableName("t_borrowcheckconfiglog")
@Data
public class BorrowCheckConfigLog
{
	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 序列号
	 */
	@TableField("serialNO")
	private String serialNO;
	/**
	 * 区域id
	 */
	@TableField("accountId")
	private Integer accountId;
	/**
	 * 用户id
	 */
	@TableField("userId")
	private Integer userId;
	/**
	 * 处理器名称
	 */
	@TableField("handlerName")
	private String handlerName;
	/**
	 * 处理器顺序
	 */
	@TableField("orderNum")
	private Integer orderNum;
	/**
	 * 处理器执行结果，0-失败，1-成功
	 */
	@TableField("executeResult")
	private Integer executeResult;
	/**
	 * 创建时间
	 */
	@TableField("createTime")
	private Timestamp createTime;

	/**
	 * 校验失败原因
	 */
	@TableField("failedReason")
	private String failedReason;

}
