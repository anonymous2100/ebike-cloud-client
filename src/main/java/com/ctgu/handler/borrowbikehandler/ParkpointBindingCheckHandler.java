package com.ctgu.handler.borrowbikehandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctgu.common.ResultMsg;
import com.ctgu.dao.BorrowCheckConfigLogMapper;
import com.ctgu.entity.BorrowCheckConfigLog;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ParkpointBindingCheckHandler
 * @Description: 站点绑定校验处理器
 */
@Slf4j
@Component
public class ParkpointBindingCheckHandler extends AbstractCheckHandler
{
	@Autowired
	private BorrowCheckConfigLogMapper borrowCheckConfigLogMapper;

	@Override
	public ResultMsg handle(BorrowBikeVO vo, String serialNO)
	{
		log.info("【站点绑定校验处理器】校验开始...");
		if (super.getConfig().getDown() == 1)
		{
			log.info("【站点绑定校验处理器】被降级，已自动跳过！");
			return super.next(vo, serialNO);
		}

		// 判断当前设备是否绑定了其他站点
		// 省略业务代码
		// ...
		log.info("【站点绑定校验处理器】校验通过！");
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
