package com.ctgu.exception;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 自定义请求头国际化处理
 */
@Component
public class MyAcceptHeaderLocaleResolver implements LocaleResolver
{
	private Locale myLocal;

	@Override
	public Locale resolveLocale(HttpServletRequest request)
	{
		return myLocal != null ? myLocal : request.getLocale();
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		this.myLocal = locale;
	}

	public Locale getLocale()
	{
		return myLocal;
	}
}
