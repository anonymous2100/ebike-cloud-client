package com.ctgu.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctgu.common.ResponseCode;
import com.ctgu.common.ResultCode;
import com.ctgu.common.ResultMsg;
import com.ctgu.entity.Machine;
import com.ctgu.entity.User;
import com.ctgu.exception.BaseException;
import com.ctgu.handler.BorrowCheckService;
import com.ctgu.pojo.BorrowBusMoney;
import com.ctgu.service.MachineService;
import com.ctgu.service.TokenService;
import com.ctgu.service.UserService;
import com.ctgu.util.IdUtil;
import com.ctgu.util.OutputUtil;
import com.ctgu.vo.input.BorrowBikeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 设备相关接口
 */
@Controller
@RequestMapping("/machine")
@Slf4j
public class MachineController
{
	@Autowired
	private MachineService machineService;
	@Autowired
	private BorrowCheckService checkService;
	// @Autowired
	// private TokenService tokenService;
	// @Autowired
	// private UserService userService;

	// /**
	// * 借车旧接口
	// *
	// * @param token
	// * @param userCode
	// * @param lon
	// * @param lat
	// * @param mapType
	// * @param ble
	// * @param session
	// * @param request
	// * @param response
	// */
	// @RequestMapping("/borrowBike")
	// public void borrowBike(String token, String userCode, Double lon, Double lat, Integer mapType, Boolean ble,
	// HttpSession session, HttpServletRequest request, HttpServletResponse response)
	// {
	// Map<String, Object> map = new HashMap<String, Object>();
	// try
	// {
	// // 省略业务代码
	// // ...
	// String header = request.getHeader("orderSource");
	// Integer orderSource;
	// /** 默认来源安卓 */
	// if (header == null)
	// {
	// orderSource = 1;
	// }
	// else
	// {
	// orderSource = (Integer.valueOf(header));
	// }
	// Machine machine = machineService.getByUserCode(userCode);
	// if (machine == null)
	// {
	// throw new BaseException("-30005", "该编号不存在");
	// }
	// User user = tokenService.getUser(token);
	// /** 用户借车车辆之前提示用户金额是否为负的 */
	// User userdb = userService.getByUserId(user.getUserId());
	// // 省略业务代码
	// // ...
	// /** 生成流水号 */
	// String serNO = UUID.randomUUID().toString();
	// BorrowBusMoney borrowBusMoney = machineService.borrowBike(serNO, userdb, user.getAccountId(), machine, ble,
	// orderSource);
	// // 省略数百行业务代码
	// // ...
	// map.put(ResponseCode.RET, ResponseCode.SUCC);
	// }
	// catch (BaseException e)
	// {
	// log.error(e.getMessage());
	// e.printStackTrace();
	// ResponseCode.printException(map, e, request);
	// }
	// OutputUtil.renderText(request, response, map);
	// }

	/**
	 * 重构借车接口：保持新接口和旧接口参数一致
	 * 
	 * @param token
	 * @param userCode
	 * @param lon
	 * @param lat
	 * @param mapType
	 * @param ble
	 * @param session
	 * @param request
	 * @param response
	 */
	@RequestMapping("/borrowBike")
	public void borrowBikeNew(String token, String userCode, Double lon, Double lat, Integer mapType, Boolean ble,
			HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			// 省略业务代码
			// ...
			Machine machine = machineService.getByUserCode(userCode);
			if (machine == null)
			{
				throw new BaseException("-30005", "该编号不存在");
			}
			BorrowBikeVO vo = new BorrowBikeVO();
			// 省略业务代码
			// ...
			// 为什么要重构：
			// 原来的代码因为保密协议的原因不能直接发出来，这里就我的理解说明一下问题所在：
			// 首先是代码太过冗长且杂乱，各种不同的判定逻辑混到一起，完全没有类型或顺序可以区分，后续增加业务代码也很麻烦；其次是这么多的代码放到一个方法里非常的难以阅读和理解；
			// 再就是没有开关配置，没有日志记录，每次都要执行所有的判定逻辑，执行完了之后也不知道本次借车/还车失败是因为什么原因，也就是到底卡在哪个步骤了；
			// 最后是代码风格和写法规范性的问题，由于历史原因，个人差异，公司制度等原因，导致明明是一个业务，但是代码风格迥异。这次优化也就顺便统一了
			//
			// 重构的要点：
			// 1、为避免重构后接口联调，接口名称、地址、请求参数和返回结果任然保持不变，仅仅是优化后端代码逻辑；
			// 2、按类型对大量的校验逻辑进行拆分和组合（用责任链模式构建）；
			// 3、每个步骤增加启用禁用开关；
			// 4、校验类使用基于数据库的动态配置（若线上部署后不能随意修改数据库也可以改成json形式的配置并放到配置中心上，可参考https://blog.csdn.net/rongtaoup/article/details/122638812）；
			// 5、增加每步执行的结果记录用于后续数据统计；
			//
			// 重构的效果：
			// 问题定位更加迅速了；数据统计也有了数据支撑了；代码的可读性和可扩展性也增强了；执行效率也略微提高了（主管感受是借车时解锁车辆比之前更快了一些）
			ResultMsg resultMsg = checkService.checkChain(vo, IdUtil.getShortId() + "_" + machine.getMachineNO());
			if (ResultCode.SUCCESS.getCode().equals(resultMsg.getCode()))
			{
				String serNO = UUID.randomUUID().toString();
				machineService.borrowBikeNew(request, vo, serNO);
				map.put(ResponseCode.RET, ResponseCode.SUCC);
			}
			else
			{
				map.put(ResponseCode.RET, ResponseCode.FAIL);
				map.put(ResponseCode.CODE, resultMsg.getCode());
				map.put(ResponseCode.MSG, resultMsg.getMsg());
				map.put(ResponseCode.DATA, resultMsg.getData());
			}
		}
		catch (BaseException e)
		{
			ResponseCode.printException(map, e, request);
		}
		OutputUtil.renderText(request, response, map);
	}

	// 省略业务代码
	// ...
}
