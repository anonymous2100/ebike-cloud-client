package com.ctgu.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("t_user")
@Data
public class User
{
	/** 用户id */
	private Integer userId;
	/** 账户id */
	private Integer accountId;

	private String country;
	/** 手机号码 */
	private String phone;
	/** 性别 */
	private String sex;
	/** 生日 */
	private String birthDay;
	/** 邮箱 */
	private String email;
}
