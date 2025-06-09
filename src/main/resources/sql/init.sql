
DROP TABLE IF EXISTS `t_borrowconfig`;
CREATE TABLE `t_borrowcheckconfig`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `accountId` int(11) NULL DEFAULT NULL COMMENT '区域id',
  `type` varchar(64)  NULL DEFAULT NULL COMMENT '校验条件分类：borrow, return',
  `module` varchar(64)  NULL DEFAULT NULL COMMENT '校验条件模块分类：accountConfig, userAccount, carStatus等',
  `handlerName` varchar(64)  NOT NULL COMMENT '处理器Bean名称',
  `handlerDesc` varchar(256)  NULL DEFAULT NULL COMMENT '处理器描述',
  `orderNum` int(11) NOT NULL DEFAULT 0 COMMENT '处理器顺序',
  `down` int(11) NOT NULL DEFAULT 0 COMMENT '是否允许跳过，0-禁止，1-允许；默认为0-禁止，若配置开启，则跳过此处理器，执行下一个',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '借还车校验处理器配置' ;

-- ----------------------------
-- Records of t_borrowcheckconfig
-- ----------------------------
INSERT INTO `t_borrowconfig` VALUES (1, 2, 'borrow', 'money', 'moneyCheckHandler', '用户余额校验处理器', 1, 0, '2023-08-16 15:44:01', '2023-08-16 15:44:01');
INSERT INTO `t_borrowconfig` VALUES (2, 2, 'borrow', 'blaklist', 'blackListCheckHandler', '黑名单校验处理器', 2, 0, '2023-08-16 15:44:01', '2023-08-31 13:59:38');
INSERT INTO `t_borrowconfig` VALUES (3, 2, 'borrow', 'identity', 'identityCheckHandler', '用户身份认证处理器', 3, 0, '2023-08-16 15:44:01', '2023-09-19 10:55:13');
INSERT INTO `t_borrowconfig` VALUES (4, 2, 'borrow', 'deposit', 'depositCheckHandler', '押金校验处理器', 4, 0, '2023-08-16 15:44:01', '2023-08-16 15:44:01');
INSERT INTO `t_borrowconfig` VALUES (5, 2, 'borrow', 'recharge', 'rechargeCheckHandler', '充值金额校验处理器', 5, 0, '2023-08-16 15:44:01', '2023-08-16 15:44:01');
INSERT INTO `t_borrowconfig` VALUES (6, 2, 'borrow', 'machineStatus', 'machineStatusCheckHandler', '车辆状态检测处理器，检测车辆低电，故障，头盔丢失等状态', 6, 0, '2023-08-16 15:44:01', '2023-10-30 17:08:56');
INSERT INTO `t_borrowconfig` VALUES (7, 2, 'borrow', 'machineStatus', 'areaCheckHandler', '区域位置校验处理器（电子围栏，禁停区，站点等）', 9, 0, '2023-08-16 15:44:01', '2023-11-17 14:25:24');
INSERT INTO `t_borrowconfig` VALUES (8, 2, 'borrow', 'machineStatus', 'helmetCheckHandler', '头盔故障校验处理器', 8, 0, '2023-08-16 15:44:01', '2023-08-16 15:44:01');
INSERT INTO `t_borrowconfig` VALUES (9, 2, 'borrow', 'faceRecognition', 'faceRecognitionCheckHandler', '人脸识别校验处理器 ： 首次人脸识别和二次人脸识别校验', 7, 0, '2023-08-16 15:44:01', '2023-11-17 14:25:27');
INSERT INTO `t_borrowconfig` VALUES (10, 2, 'borrow', 'parkpoint', 'parkpointBindingCheckHandler', '停车点与车辆绑定校验器', 10, 1, '2023-08-30 15:00:09', '2023-11-17 14:25:13');

-- ----------------------------
-- Table structure for t_borrowcheckconfiglog
-- ----------------------------
DROP TABLE IF EXISTS `t_borrowconfiglog`;
CREATE TABLE `t_borrowcheckconfiglog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `serialNO` varchar(128)  NOT NULL COMMENT '执行序列号',
  `accountId` int(11) NULL DEFAULT NULL COMMENT '区域id',
  `userId` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `handlerName` varchar(64)  NOT NULL COMMENT '处理器名称',
  `orderNum` int(11) NOT NULL DEFAULT 0 COMMENT '处理器顺序',
  `executeResult` int(11) NOT NULL COMMENT '处理器执行结果，0-失败，1-成功',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `failedReason` varchar(255)  NULL DEFAULT NULL COMMENT '校验失败原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = '借车还车校验日志' ;
