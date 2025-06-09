package com.ctgu.exception;

import com.ctgu.common.ResultCode;

/**
 * 基础异常
 */
public class BaseException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public BaseException()
	{
	}

	public BaseException(String msg)
	{
		super(ResultCode.FAIL.getCode() + "%" + msg);
	}

	public BaseException(String code, String msg)
	{
		super(code + "%" + msg);
	}

	public BaseException(Integer code, String msg)
	{
		super(code + "%" + msg);
	}
}
