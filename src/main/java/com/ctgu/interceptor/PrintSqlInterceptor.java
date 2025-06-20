package com.ctgu.interceptor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义拦截器，打印执行的sql
 */
@Slf4j
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }) })
public class PrintSqlInterceptor implements Interceptor
{
	@Override
	public Object intercept(Invocation invocation) throws Throwable
	{
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1)
		{
			parameter = invocation.getArgs()[1];
		}
		String sqlId = mappedStatement.getId();
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		long start = System.currentTimeMillis();
		Object returnValue = invocation.proceed();
		long time = System.currentTimeMillis() - start;
		showSql(configuration, boundSql, time, sqlId);
		return returnValue;
	}

	private static void showSql(Configuration configuration, BoundSql boundSql, long time, String sqlId)
	{
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		// 替换空格、换行、tab缩进等
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null)
		{
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
			{
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
			}
			else
			{
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings)
				{
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName))
					{
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
					else if (boundSql.hasAdditionalParameter(propertyName))
					{
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		logs(time, sql, sqlId);
	}

	private static String getParameterValue(Object obj)
	{
		String value;
		if (obj instanceof String)
		{
			value = "'" + obj.toString() + "'";
		}
		else if (obj instanceof Date)
		{
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		}
		else
		{
			if (obj != null)
			{
				value = obj.toString();
			}
			else
			{
				value = "";
			}
		}
		return value.replace("$", "\\$");
	}

	private static void logs(long time, String sql, String sqlId)
	{
		StringBuilder sb = new StringBuilder().append(" Time：").append(time).append(" ms - ID：").append(sqlId).append(
				"\n").append("Execute SQL：\n").append(sql).append("\n");
		log.info(sb.toString());
		// log.info("\nFormated SQL: \n{}", new SQLFormatterUtil().format(sql));
	}

	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties0)
	{
	}
}
