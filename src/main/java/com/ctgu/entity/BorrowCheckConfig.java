package com.ctgu.entity;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 *  处理器配置实体类
 */
@TableName("t_borrowcheckconfig")
@Data
public class BorrowCheckConfig
{
	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 区域id
	 */
	@TableField("accountId")
	private Integer accountId;
	/**
	 * 校验条件分类：borrow, return
	 */
	@TableField("type")
	private String type;
	/**
	 * 校验条件模块分类：accountConfig, userAccount, carStatus等
	 */
	@TableField("module")
	private String module;
	/**
	 * 处理器名称
	 */
	@TableField("handlerName")
	private String handlerName;
	/**
	 * 处理器描述
	 */
	@TableField("handlerDesc")
	private String handlerDesc;
	/**
	 * 处理器顺序
	 */
	@TableField("orderNum")
	private Integer orderNum;
	/**
	 * 是否允许跳过，0-禁止，1-允许；默认为0-禁止，若配置开启，则跳过此处理器，执行下一个
	 */
	@TableField("down")
	private Integer down;
	/**
	 * 创建时间
	 */
	@TableField("createTime")
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	@TableField("updateTime")
	private Timestamp updateTime;
}
