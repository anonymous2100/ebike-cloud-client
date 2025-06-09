package com.ctgu.common;

import lombok.Data;

/**
 * 统一返回数据格式
 */
@Data
public class ResultMsg<T>
{
	/**
	 * 结果布尔值
	 */
	private Integer ret;
	/**
	 * 错误代码
	 */
	private String code;
	/**
	 * 结果内容
	 */
	private T data;
	/**
	 * 描述
	 */
	private String msg;

	private Integer total;

	/**
	 * success
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> success()
	{
		ResultMsg<T> r = new ResultMsg<T>();
		r.setRet(1);
		r.setCode(ResultCode.SUCCESS.getCode() + "");
		return r;
	}

	/**
	 * success
	 *
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> success(T data)
	{
		ResultMsg<T> r = new ResultMsg<T>();
		r.setRet(1);
		r.setCode(ResultCode.SUCCESS.getCode() + "");
		r.setData(data);
		return r;
	}

	/**
	 * success
	 *
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> success(T data, Integer total)
	{
		ResultMsg<T> r = new ResultMsg<T>();
		r.setRet(1);
		r.setCode(ResultCode.SUCCESS.getCode() + "");
		r.setData(data);
		r.setTotal(total);
		return r;
	}

	/**
	 * failure
	 *
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> fail(ResultCode code)
	{
		return fail(code.getCode(), code.getDesc());
	}

	/**
	 * failure
	 *
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> fail(String msg)
	{
		return fail(ResultCode.FAIL.getCode(), msg);
	}

	/**
	 * failure
	 *
	 * @param code
	 * @param message
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> fail(String code, String message)
	{
		return fail(code, message, null);
	}

	/**
	 * failure
	 *
	 * @param code
	 * @param msg
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResultMsg<T> fail(String code, String msg, T data)
	{
		ResultMsg<T> r = new ResultMsg<T>();
		r.setRet(0);
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}
}
