package com.ctgu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 设备
 */
@Data
@TableName("t_machine")
public class Machine implements Serializable
{
	private static final long serialVersionUID = 1863974841343799311L;
	/**
	 * 设备id
	 */
	@TableId("machineId")
	private Integer machineId;
	/**
	 * 账号id
	 */
	@TableField("accountId")
	private Integer accountId;
	/**
	 * 设备编号
	 */
	@TableField("machineNO")
	private String machineNO;
	/**
	 * 车辆描述
	 */
	@TableField("remark")
	private String remark;
}
