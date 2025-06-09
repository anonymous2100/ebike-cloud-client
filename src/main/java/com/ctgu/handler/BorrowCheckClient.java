package com.ctgu.handler;

import org.springframework.stereotype.Component;

import com.ctgu.common.ResultCode;
import com.ctgu.common.ResultMsg;
import com.ctgu.handler.borrowbikehandler.AbstractCheckHandler;
import com.ctgu.vo.input.BorrowBikeVO;

/**
 * @ClassName: CheckClient
 * @Description: 组装校验链的客户端
 */
@Component
public class BorrowCheckClient
{
	public ResultMsg executeChain(AbstractCheckHandler handler, BorrowBikeVO vo, String serialNO)
	{
		ResultMsg result = handler.handle(vo, serialNO);
		if (result.getCode() != ResultCode.SUCCESS.getCode())
		{
			return result;
		}
		return result;
	}
}
