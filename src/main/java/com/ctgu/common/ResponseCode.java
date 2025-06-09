package com.ctgu.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

import com.ctgu.exception.BaseException;

/**
 * 响应代码，兼容旧代码
 */
public class ResponseCode
{
	/** 成功 */
	public final static Integer SUCC = 1;
	/** 失败 */
	public final static Integer FAIL = 0;

	/** 结果布尔值 */
	public final static String RET = "ret";
	/** 描述 */
	public final static String MSG = "msg";
	/** 错误代码 */
	public final static String CODE = "code";
	/** 结果内容 */
	public final static String DATA = "data";

	/** 消息响应 */
	public static void printException(Map<String, Object> map, BaseException e, HttpServletRequest request)
	{
		map.put(ResponseCode.RET, ResponseCode.FAIL);

		String msg = e.getMessage();
		if (msg != null && msg.contains("%"))
		{
			String[] array = msg.split("%");
			RequestContext requestContext = new RequestContext(request);
			String message = requestContext.getMessage(array[0]);
			map.put(ResponseCode.CODE, array[0]);
			map.put(ResponseCode.MSG, message);
		}
		else
		{
			map.put(ResponseCode.CODE, "-101");
			map.put(ResponseCode.MSG, e.getMessage());
		}
	}
}
