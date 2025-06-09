#责任链模式实际使用优化（基于配置的方式）

## 一、责任链模式的定义与特点
　　责任链（Chain of Responsibility）模式的定义：责任链模式也叫职责链模式，为了避免请求发送者与多个请求处理者耦合在一起，将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。  


　　在责任链模式中，客户只需要将请求发送到责任链上即可，无须关心请求的处理细节和请求的传递过程，所以责任链将请求的发送者和请求的处理者解耦了。  


## 二、责任链模式优缺点
　　责任链模式是一种对象行为型模式，其主要优点如下：  

1. 降低了对象之间的耦合度。该模式使得一个对象无须知道到底是哪一个对象处理其请求以及链的结构，发送者和接收者也无须拥有对方的明确信息。  
2. 增强了系统的可扩展性。可以根据需要增加新的请求处理类，满足开闭原则。  
3. 增强了给对象指派职责的灵活性。当工作流程发生变化，可以动态地改变链内的成员或者调动它们的次序，也可动态地新增或者删除责任。  
4. 责任链简化了对象之间的连接。每个对象只需保持一个指向其后继者的引用，不需保持其他所有处理者的引用，这避免了使用众多的 if 或者 if···else 语句。  
5. 责任分担。每个类只需要处理自己该处理的工作，不该处理的传递给下一个对象完成，明确各类的责任范围，符合类的单一职责原则。  


其主要缺点如下：  
1. 不能保证每个请求一定被处理。由于一个请求没有明确的接收者，所以不能保证它一定会被处理，该请求可能一直传到链的末端都得不到处理。  

2. 对比较长的职责链，请求的处理可能涉及多个处理对象，系统性能将受到一定影响。  

3. 职责链建立的合理性要靠客户端来保证，增加了客户端的复杂性，可能会由于职责链的错误设置而导致系统出错，如可能会造成循环调用。  


## 三、责任链模式的实现
　　通常情况下，可以通过数据链表来实现职责链模式的数据结构。  

　　职责链模式主要包含以下角色。  

抽象处理者（Handler）角色：定义一个处理请求的接口，包含抽象处理方法和一个后继连接。  

具体处理者（Concrete Handler）角色：实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者。  

客户类（Client）角色：创建处理链，并向链头的具体处理者对象提交请求，它不关心处理细节和请求的传递过程。  

　　其结构图如图所示：  
![这是图片](/pic/1006268081.png)



## 四、代码优化要点:  

1. 具体处理者使用配置中心或者数据库配置;  

2. 每个步骤增加启用禁用开关；  

3. 增加每步执行的结果记录用于后续数据统计;


使用配置中心的方法可以参考以下代码： https://blog.csdn.net/rongtaoup/article/details/122638812，  

这里我们选择的是数据库表的配置方式；
sql语句如下：

```
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

```

项目中创建的具体处理者如下（实际业务相关的代码已全程删除，仅保留基本内容）:

```
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

```

这里顺便提一下，使用spring可以自动注入抽象接口的实现类: https://www.cnblogs.com/xfeiyun/p/15654243.html









 

