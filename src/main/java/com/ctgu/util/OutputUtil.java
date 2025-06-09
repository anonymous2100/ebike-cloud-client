package com.ctgu.util;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP输入工具类 2014-12-17 下午02:29:21
 */
public class OutputUtil
{
	/** 直接输出纯字符串. */
	public static void renderText(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
	{
		String text = JSON.toJSONString(map);
		response.setContentType("application/json;charset=UTF-8");
		/* 判断是否是需要支持JSONP */
		String callback = request.getParameter("callback");
		if (callback != null && !"".equals(callback))
		{
			response.setContentType("application/jsonp;charset=UTF-8");
			text = callback + "(" + text + ")";
		}
		try
		{
			response.getWriter().write(text);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/** 直接输出纯字符串. */
	public static void renderText(HttpServletRequest request, HttpServletResponse response, String result)
	{
		response.setContentType("application/json;charset=UTF-8");
		try
		{
			response.getWriter().write(result);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/** 支付宝异步通知恢复. */
	public static void renderHtml(HttpServletRequest request, HttpServletResponse response, String content)
	{
		response.setContentType("text/html;charset=UTF-8");
		try
		{
			response.getWriter().write(content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
