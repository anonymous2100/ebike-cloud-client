package com.ctgu.handler.borrowbikehandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctgu.common.ResultMsg;
import com.ctgu.dao.BorrowCheckConfigLogMapper;
import com.ctgu.entity.BorrowCheckConfigLog;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RechargeCheckHandler
 * @Description: 充值金额和骑行最低金额校验处理器
 */
@Slf4j
@Component
public class RechargeCheckHandler extends AbstractCheckHandler
{
	@Autowired
	private BorrowCheckConfigLogMapper borrowCheckConfigLogMapper;

	@Override
	public ResultMsg handle(BorrowBikeVO vo, String serialNO)
	{
		log.info("【充值金额和骑行最低金额校验处理器】校验开始...");
		if (super.getConfig().getDown() == 1)
		{
			log.info("【充值金额和骑行最低金额校验处理器】被降级，已自动跳过！");
			return super.next(vo, serialNO);
		}

		// 此处业务代码省略
		// 省略业务代码
		// ...
		log.info("【充值金额和骑行最低金额校验处理器】校验通过！");
		BorrowCheckConfigLog bccLog = borrowCheckConfigLogMapper.findByNameAndSerialNO(
				this.getConfig().getHandlerName(), serialNO);
		if (bccLog != null)
		{
			bccLog.setExecuteResult(1);
			borrowCheckConfigLogMapper.updateByPrimaryKeySelective(bccLog);
		}
		return super.next(vo, serialNO);
	}

}
