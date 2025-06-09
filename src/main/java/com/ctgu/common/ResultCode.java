package com.ctgu.common;

import lombok.Getter;

/**
 */
@Getter
public enum ResultCode
{
	/**
	 * 返回结果码：成功
	 */
	SUCCESS("1", "成功"),
	/**
	 * 返回结果码：失败
	 */
	FAIL("0", "失败"),
	/**
	 * 分账配置未配置
	 */
	ADAPAY_NOT_CONFIG("-5036", "adaPay_not_config"),
	/**
	 * 分账配置已存在
	 */
	ADAPAYCONFIG_HAVE_EXIST("-5037", "adaPayConfig_have_exist"),
	/**
	 * 汇付对公账户注册文件不正确
	 */
	MERCHANT_FILE_INVALID("-4000", "汇付对公账户注册文件不正确"),
	/**
	 * 初始化汇付配置失败
	 */
	ADAPAY_INIT_ERROR("-4001", "初始化汇付配置失败"),
	/**
	 * 创建汇付企业用户失败
	 */
	CREATE_BUSINESS_USER_ERROR("-4002", "创建汇付企业用户失败"),
	/**
	 * 创建汇付个人用户失败
	 */
	CREATE_PERSON_USER_ERROR("-4003", "创建汇付个人用户失败"),
	/**
	 * 创建汇付结算用户失败，银行卡与身份证不匹配
	 */
	CREATE_BILLING_USER_ERROR("-4004", "创建汇付结算用户失败，银行卡与身份证不匹配"),
	/**
	 * 汇付商户注册信息正在审核中
	 */
	MERCHANT_APPROVING_ONGOING("-4005", "汇付商户注册信息正在审核中"),
	/**
	 * 汇付商户注册信息已被驳回
	 */
	MERCHANT_APPROVING_REJECTED("-4006", "汇付商户注册信息已被驳回"),
	/**
	 * 汇付实名认证不通过或用户已存在，请重新创建
	 */
	MERCHANT_PERSON_ID_REJECTED("-4007", "汇付实名认证不通过，请重新创建"),
	/**
	 * 汇付延时分账支付配置未开启
	 */
	ADA_PAY_TYPE_ERROR("-4008", "汇付延时分账支付配置未开启"),

	/**
	 * 数据已存在
	 */
	DATA_EXIST("-5000", "数据已存在"),
	/**
	 * 数据不存在
	 */
	DATA_NOT_EXIST("-5001", "数据不存在"),
	/**
	 * 数据已被删除
	 */
	DATA_ALREADY_DELETED("-5002", "数据已被删除"),
	/**
	 * 请求参数不正确
	 */
	REQUEST_PARAM_INVALID("-5003", "请求参数不正确"),
	/**
	 * 身份证号不正确
	 */
	REQUEST_PARAM_ID_CARD_INVALID("-5004", "身份证号不正确"),
	/**
	 * 手机号不正确
	 */
	REQUEST_PARAM_PHONE_INVALID("-5005", "手机号不正确"),
	/**
	 * 车辆已经与其他站点绑定
	 */
	MACHINE_PARKPOINT_ALREADY_BINDED("-5006", "车辆已经与其他站点绑定"),
	/**
	 * 车辆未绑定商户
	 */
	MACHINE_MERCHANT_NOT_BINDED("-5007", "车辆未绑定商户"),
	/**
	 * 申请已被审批，不允许修改
	 */
	APPLY_DATA_APPROVED("-6002", "申请已被审批，不允许修改"),
	/**
	 * 车辆已预约站点
	 */
	MACHINE_ALREADY_RESERVED("-6100", "车辆已预约站点"),
	/**
	 * 预约站点容量已满
	 */
	PARKPOINT_ALREADY_FULL("-6101", "预约站点容量已满"),
	/**
	 * 预约时间不能小于当前时间
	 */
	RESERVE_TIME_PASSED("-6102", "预约时间不能小于当前时间"),
	/**
	 * 结束时间不能小于开始时间
	 */
	TIME_DURATION_ERROR("-6103", "结束时间不能小于开始时间"),
	/**
	 * 固定站点不支持预约
	 */
	PARKPOINT_IS_SOLID("-6104", "固定站点不支持预约"),
	/**
	 * 投资加盟车辆不允许下架
	 */
	OFF_SHELVES_DENIED("-6105", "投资加盟车辆不允许下架");

	private String code;
	private String desc;

	ResultCode(String code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据code查找
	 */
	public static String findDescByCode(String code)
	{
		for (ResultCode statusEnum : ResultCode.values())
		{
			if (statusEnum.getCode().equals(code))
			{
				return statusEnum.getDesc();
			}
		}
		throw new IllegalArgumentException("code is invalid");
	}

	/**
	 * 根据desc查找
	 */
	public static ResultCode findEnumByDesc(String desc)
	{
		for (ResultCode statusEnum : ResultCode.values())
		{
			if (statusEnum.getDesc().equals(desc))
			{
				return statusEnum;
			}
		}
		throw new IllegalArgumentException("desc is invalid");
	}
}
