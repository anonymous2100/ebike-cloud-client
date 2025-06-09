package com.ctgu.handler.borrowbikehandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctgu.common.ResultMsg;
import com.ctgu.dao.BorrowCheckConfigLogMapper;
import com.ctgu.entity.BorrowCheckConfigLog;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AreaCheckHandler
 * @Description: 区域位置校验处理器（电子围栏，禁停区，站点等）
 */
@Slf4j
@Component
public class AreaCheckHandler extends AbstractCheckHandler
{
	@Autowired
	private BorrowCheckConfigLogMapper borrowCheckConfigLogMapper;

	@Override
	public ResultMsg handle(BorrowBikeVO vo, String serialNO)
	{
		log.info("【区域位置校验处理器】校验开始...");
		if (super.getConfig().getDown() == 1)
		{
			log.info("【区域位置校验处理器】被降级，已自动跳过！");
			return super.next(vo, serialNO);
		}
		/** 判断车辆是否在电子围栏内 */
		// 省略业务代码
		// ...

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
