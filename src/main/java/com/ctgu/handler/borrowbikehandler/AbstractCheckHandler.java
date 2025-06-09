package com.ctgu.handler.borrowbikehandler;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.ctgu.common.ResultMsg;
import com.ctgu.dao.BorrowCheckConfigLogMapper;
import com.ctgu.entity.BorrowCheckConfig;
import com.ctgu.entity.BorrowCheckConfigLog;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AbstractCheckHandler
 * @Description: 处理器抽象类
 */
@Slf4j
public abstract class AbstractCheckHandler
{
	/**
	 * 当前处理器持有的下一个处理器的引用
	 */
	@Getter
	@Setter
	private AbstractCheckHandler nextHandler;
	/**
	 * 处理器配置
	 */
	@Setter
	@Getter
	protected BorrowCheckConfig config;
	/**
	 * 记录处理器处理流程日志
	 */
	@Autowired
	private BorrowCheckConfigLogMapper borrowCheckConfigLogMapper;

	/**
	 * 链路传递
	 */
	protected ResultMsg next(BorrowBikeVO vo, String serialNO)
	{
		log.info("调用next方法，config={}，nextHandler={}", config, nextHandler);
		// 下一个链路没有处理器了，直接返回
		if (Objects.isNull(nextHandler))
		{
			return ResultMsg.success();
		}
		// 首先生成一条调用成功记录，然后在每个处理器处理过程中更新处理结果
		BorrowCheckConfigLog bccLog = new BorrowCheckConfigLog();
		bccLog.setSerialNO(serialNO);
		bccLog.setAccountId(vo.getUser().getAccountId());
		bccLog.setUserId(vo.getUser().getUserId());
		bccLog.setHandlerName(nextHandler.getConfig().getHandlerName());
		bccLog.setOrderNum(nextHandler.getConfig().getOrderNum());
		bccLog.setExecuteResult(0);
		borrowCheckConfigLogMapper.insertSelective(bccLog);
		// 链路传递
		return nextHandler.handle(vo, serialNO);
	}

	/**
	 * 处理器执行方法，即具体的校验逻辑
	 */
	public abstract ResultMsg handle(BorrowBikeVO vo, String serialNo);
}
