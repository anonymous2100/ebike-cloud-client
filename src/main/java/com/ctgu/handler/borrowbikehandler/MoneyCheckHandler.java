package com.ctgu.handler.borrowbikehandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctgu.common.ResultMsg;
import com.ctgu.dao.BorrowCheckConfigLogMapper;
import com.ctgu.entity.BorrowCheckConfigLog;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: MoneyCheckHandler
 * @Description: 用户余额校验处理器
 */
@Slf4j
@Component
public class MoneyCheckHandler extends AbstractCheckHandler
{
	@Autowired
	private BorrowCheckConfigLogMapper borrowCheckConfigLogMapper;

	@Override
	public ResultMsg handle(BorrowBikeVO vo, String serialNO)
	{
		log.info("【用户余额校验处理器】校验开始...");
		if (super.getConfig().getDown() == 1)
		{
			log.info("【用户余额校验处理器】被降级，已自动跳过！");
			return super.next(vo, serialNO);
		}

		/** 用户借车车辆之前提示用户金额是否为负的 */
		// 省略业务代码
		// ...
		log.info("【用户余额校验处理器】校验通过！");
		BorrowCheckConfigLog bccLog = new BorrowCheckConfigLog();
		bccLog.setSerialNO(serialNO);
		bccLog.setAccountId(vo.getUser().getAccountId());
		bccLog.setUserId(vo.getUser().getUserId());
		bccLog.setHandlerName(this.getConfig().getHandlerName());
		bccLog.setOrderNum(this.getConfig().getOrderNum());
		bccLog.setExecuteResult(1);
		borrowCheckConfigLogMapper.insertSelective(bccLog);
		return super.next(vo, serialNO);
	}
}
