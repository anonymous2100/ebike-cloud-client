package com.ctgu.handler;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctgu.common.ResultCode;
import com.ctgu.common.ResultMsg;
import com.ctgu.dao.BorrowCheckConfigLogMapper;
import com.ctgu.dao.BorrowCheckConfigMapper;
import com.ctgu.entity.BorrowCheckConfig;
import com.ctgu.handler.borrowbikehandler.AbstractCheckHandler;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: CheckService
 * @Description: 查询数据库配置，组装借车校验链路
 */
@Slf4j
@Component
public class BorrowCheckService
{
	/**
	 * 使用Spring注入所有继承了AbstractCheckHandler抽象类的Spring Bean， Map的Key对应Bean的name,Value是name对应相应的Bean
	 */
	@Resource
	private Map<String, AbstractCheckHandler> handlerMap;
	@Resource
	private BorrowCheckConfigMapper borrowCheckConfigMapepr;
	@Autowired
	private BorrowCheckConfigLogMapper borrowCheckConfigLogMapper;
	@Resource
	private BorrowCheckClient checkClient;

	/**
	 * 参数校验，链式处理
	 * 
	 * @param BorrowBikeVO
	 *            链路处理请求参数
	 * @param serialNO
	 *            链路处理序列号，每一次进入链路都会生成不同的序列号
	 * @return ResultMsg 返回链路处理结果
	 */
	public ResultMsg checkChain(BorrowBikeVO vo, String serialNO)
	{
		List<BorrowCheckConfig> handlerConfigs = borrowCheckConfigMapepr.selectAllByType("borrow");
		AbstractCheckHandler handler = this.getHandler(handlerConfigs, 0);
		ResultMsg executeChainResult = checkClient.executeChain(handler, vo, serialNO);
		if (executeChainResult.getRet() != Integer.parseInt(ResultCode.SUCCESS.getCode()))
		{
			log.info("借车校验处理器链执行失败，ResultCode={}，ResultMsg=={}, data={}", executeChainResult.getCode(),
					executeChainResult.getMsg(), executeChainResult.getData());
			// 记录本次借车失败具体原因
			borrowCheckConfigLogMapper.updateFailedReason(serialNO, "code=" + executeChainResult.getCode() + ",data="
					+ executeChainResult.getData() + ",msg=" + executeChainResult.getMsg());
			return executeChainResult;
		}
		return ResultMsg.success();
	}

	/**
	 * 获取处理器
	 */
	private AbstractCheckHandler getHandler(List<BorrowCheckConfig> configList, int orderNum)
	{
		if (configList == null || configList.size() == 0 || orderNum > configList.size())
		{
			return null;
		}
		String handler = configList.get(orderNum).getHandlerName();
		if (StringUtils.isBlank(handler))
		{
			return null;
		}
		AbstractCheckHandler abstractCheckHandler = handlerMap.get(handler);
		if (Objects.isNull(abstractCheckHandler))
		{
			return null;
		}
		abstractCheckHandler.setConfig(configList.get(orderNum));
		if (orderNum < configList.size() - 1)
		{
			abstractCheckHandler.setNextHandler(this.getHandler(configList, orderNum + 1));
		}
		return abstractCheckHandler;
	}
}
