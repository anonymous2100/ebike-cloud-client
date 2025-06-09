package com.ctgu.config;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig
{
	@Bean
	public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor()
	{
		return new BeanPostProcessor()
		{
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
			{
				if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider)
				{
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(
					List<T> mappings)
			{
				List<T> copy = mappings.stream().filter(mapping -> mapping.getPatternParser() == null).collect(
						Collectors.toList());
				mappings.clear();
				mappings.addAll(copy);
			}

			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean)
			{
				try
				{
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					throw new IllegalStateException(e);
				}
			}
		};
	}

	/**
	 * 添加摘要信息 这里是接口的描述配置，不重要
	 */
	private ApiInfo apiInfo()
	{
		// 用ApiInfoBuilder进行定制
		return new ApiInfoBuilder().title("快码租赁系统_接口文档").description("用于生成RESTapi风格的接口")
				// .contact(new Contact(Global.getName(), null, null))
				// .version("版本号:" + Global.getVersion())
				.build();
	}

	@Bean(value = "defaultApi2")
	public Docket defaultApi2()
	{
		Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("api").enable(Boolean.TRUE).apiInfo(
				apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				// 只扫描有ApiOperation注解的方法
				// .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 对所有路径进行扫描
				.paths(PathSelectors.any()).build();
		return docket;
	}
}
