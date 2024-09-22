/*
 Navicat Premium Dump SQL

 Source Server         : 自测（MySQL）
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : 1.92.74.70:3306
 Source Schema         : mm_daas

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 22/09/2024 14:16:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_base
-- ----------------------------
DROP TABLE IF EXISTS `api_base`;
CREATE TABLE `api_base`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `api_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api名称',
  `api_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api路径',
  `req_method` tinyint(1) NOT NULL DEFAULT 1 COMMENT '请求方法 1 get 2post 3put 4delete',
  `resp_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '响应数据格式类型',
  `api_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `template_sql` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行sql',
  `api_status` tinyint(4) NULL DEFAULT 0 COMMENT '状态 0启用 1停用',
  `data_source_id` bigint(20) NULL DEFAULT NULL COMMENT '对应数据源id',
  `main_table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主表名称',
  `source_table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '数据源表名称',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '业务类型id',
  `exec_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'R' COMMENT '操作类型：CRUD',
  `open_hump` tinyint(1) NULL DEFAULT 1 COMMENT '是否开启驼峰命名返回',
  `api_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1 普通接口  2 接口组',
  `api_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口id数组，用逗号隔开',
  `connect_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联类型：join、union',
  `join_keys` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key数组，用逗号隔开（关联类型为join时才需要填写）',
  `forbid_transfer_keys` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防止转义字符串，逗号隔开',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除， 0. 正常、1. 删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 353 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of api_base
-- ----------------------------
INSERT INTO `api_base` VALUES (347, '司机二级户信息统计表', 'driver/secondary/information', 2, 'list', '', 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nDATE_FORMAT(withdrawal_time, \'%Y-%m-%d %H:%i:%s\') as withdrawal_time,\r\nwithdrawal_amount,\r\nwithdrawal_account,  \r\nDATE_FORMAT(deposit_time, \'%Y-%m-%d %H:%i:%s\') as deposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\ncarrier_name asc,deposit_number asc', 0, 37, '', '', NULL, 'R', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `api_base` VALUES (348, '数仓上线前需求成本表', 'demand/cost/statement', 2, 'list', '', 'select * from ads_carrier_settle_statis ORDER by settle_time desc, carrier4payed4amt desc', 0, 37, '', '', NULL, 'R', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `api_base` VALUES (349, '开票情况统计', 'invoice/state/statistics', 2, 'list', '', 'select \r\noffice_id,\r\noffice_name,\r\nservice_provider,\r\nenterprise_id,\r\nenterprise_name,\r\nreceipt_batch_id,\r\nreceipt_batch_no,\r\nticket_id,\r\ninvoice_no,\r\norder_id,\r\norder_no,\r\ncarrier_id,\r\nname,\r\nidcard_no,\r\nDATE_FORMAT(receipt_confirm_date, \'%Y-%m-%d %H:%i:%s\') as receipt_confirm_date,\r\nDATE_FORMAT(sign_time, \'%Y-%m-%d %H:%i:%s\') as sign_time,\r\nsum_shipper4ticket4amt,\r\nshipper4payed4amt,\r\ncarrier4payed4amt\r\nfrom ads_carrier_receipt_statis\r\norder by idcard_no asc, sum_shipper4ticket4amt desc, shipper4payed4amt desc, carrier4payed4amt desc', 0, 37, '', '', NULL, 'R', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `api_base` VALUES (350, '运单取消统计表', 'order/cancel/statistics', 2, 'list', '', 'SELECT \r\n  `internal_dept`,\r\n  `enterprise_name`,\r\n  `order_no`,\r\n  DATE_FORMAT(`sign_time`, \'%Y-%m-%d %H:%i:%s\') AS `sign_time`,\r\n  `order_amount`,\r\n  `carrier_name`,\r\n  `carrier_idcard`,\r\n  `pay_carrier_amount`,\r\n  DATE_FORMAT(`pay_carrier_time`, \'%Y-%m-%d %H:%i:%s\') AS `pay_carrier_time`,\r\n  `order_status`,\r\n  `order_refund_amount`,\r\n  DATE_FORMAT(`create_time`, \'%Y-%m-%d %H:%i:%s\') AS `create_time`\r\nFROM \r\n  `ads_order_cancel_statis`\r\n  order by sign_time desc\r\n', 0, 37, '', '', NULL, 'R', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `api_base` VALUES (351, '未票收入报表', 'receipts/unbilled/income', 2, 'list', '', 'select * from ads_none_receipt_income_statis\r\nORDER by create_time desc', 0, 37, '', '', NULL, 'R', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `api_base` VALUES (352, '未票成本报表', 'receipts/unbilled/cost', 2, 'list', '', 'select * from ads_none_receipt_cost_statis \r\nORDER by create_time desc', 0, 37, '', '', NULL, 'R', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for api_base_log
-- ----------------------------
DROP TABLE IF EXISTS `api_base_log`;
CREATE TABLE `api_base_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `api_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '报表id',
  `template_sql` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '发布语句',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发布备注',
  `op_id` bigint(20) NULL DEFAULT NULL COMMENT '操作id',
  `op_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 575 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '动态报表操作日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of api_base_log
-- ----------------------------
INSERT INTO `api_base_log` VALUES (549, 348, 'select * from car', NULL, NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (550, 349, 'select * from car', NULL, NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (551, 350, 'select * from car', NULL, NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (552, 351, 'select * from car', NULL, NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (553, 352, 'select * from car', NULL, NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (554, 347, 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nwithdrawal_time,\r\nwithdrawal_account,  \r\ndeposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\nwithdrawal_time desc', '1', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (555, 349, 'select * from ads_carrier_receipt_statis', '1', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (556, 349, 'select \r\noffice_id,\r\noffice_name,\r\nservice_provider,\r\nenterprise_id,\r\nenterprise_name,\r\nreceipt_batch_id,\r\nreceipt_batch_no,\r\nticket_id,\r\ninvoice_no,\r\norder_id,\r\norder_no,\r\ncarrier_id,\r\nname,\r\nidcard_no,\r\nreceipt_confirm_date,\r\nsign_time,\r\nsum_shipper4ticket4amt,\r\nshipper4payed4amt,\r\ncarrier4payed4amt\r\nfrom ads_carrier_receipt_statis\r\norder by  sign_time desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (557, 347, 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nDATE_FORMAT(withdrawal_time, \'%Y-%m-%d %H:%i:%s\') as withdrawal_time,\r\nwithdrawal_account,  \r\nDATE_FORMAT(deposit_time, \'%Y-%m-%d %H:%i:%s\') as deposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\nwithdrawal_time desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (558, 349, 'select \r\noffice_id,\r\noffice_name,\r\nservice_provider,\r\nenterprise_id,\r\nenterprise_name,\r\nreceipt_batch_id,\r\nreceipt_batch_no,\r\nticket_id,\r\ninvoice_no,\r\norder_id,\r\norder_no,\r\ncarrier_id,\r\nname,\r\nidcard_no,\r\nreceipt_confirm_date,\r\nDATE_FORMAT(sign_time, \'%Y-%m-%d %H:%i:%s\') as sign_time,\r\nsum_shipper4ticket4amt,\r\nshipper4payed4amt,\r\ncarrier4payed4amt\r\nfrom ads_carrier_receipt_statis\r\norder by  sign_time desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (559, 349, 'select \r\noffice_id,\r\noffice_name,\r\nservice_provider,\r\nenterprise_id,\r\nenterprise_name,\r\nreceipt_batch_id,\r\nreceipt_batch_no,\r\nticket_id,\r\ninvoice_no,\r\norder_id,\r\norder_no,\r\ncarrier_id,\r\nname,\r\nidcard_no,\r\nDATE_FORMAT(receipt_confirm_date, \'%Y-%m-%d %H:%i:%s\') as receipt_confirm_date,\r\nDATE_FORMAT(sign_time, \'%Y-%m-%d %H:%i:%s\') as sign_time,\r\nsum_shipper4ticket4amt,\r\nshipper4payed4amt,\r\ncarrier4payed4amt\r\nfrom ads_carrier_receipt_statis\r\norder by  sign_time desc', '3', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (560, 348, 'select * from ads_carrier_settle_statis', '1', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (561, 351, 'select * from ads_none_receipt_income_statis', '1', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (562, 350, 'select * from ads_order_cancel_statis', '1', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (563, 352, 'select * from ads_none_receipt_cost_statis', '1', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (564, 347, 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nDATE_FORMAT(withdrawal_time, \'%Y-%m-%d %H:%i:%s\') as withdrawal_time,\r\nwithdrawal_amount,\r\nwithdrawal_account,  \r\nDATE_FORMAT(deposit_time, \'%Y-%m-%d %H:%i:%s\') as deposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\nwithdrawal_time desc', '3', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (565, 350, 'SELECT \r\n  `internal_dept`,\r\n  `enterprise_name`,\r\n  `order_no`,\r\n  DATE_FORMAT(`sign_time`, \'%Y-%m-%d %H:%i:%s\') AS `sign_time`,\r\n  `order_amount`,\r\n  `carrier_name`,\r\n  `carrier_idcard`,\r\n  `pay_carrier_amount`,\r\n  DATE_FORMAT(`pay_carrier_time`, \'%Y-%m-%d %H:%i:%s\') AS `pay_carrier_time`,\r\n  `order_status`,\r\n  `order_refund_amount`,\r\n  DATE_FORMAT(`create_time`, \'%Y-%m-%d %H:%i:%s\') AS `create_time`\r\nFROM \r\n  `ads_order_cancel_statis`\r\n  order by sign_time desc\r\n', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (566, 351, 'select * from ads_none_receipt_income_statis\r\nORDER by create_time desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (567, 352, 'select * from ads_none_receipt_cost_statis \r\nORDER by create_time desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (568, 348, 'select * from ads_carrier_settle_statis ORDER by settle_time desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (569, 348, 'select * from ads_carrier_settle_statis ORDER by settle_time desc, carrier4payed4amt desc', '3', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (570, 347, 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nDATE_FORMAT(withdrawal_time, \'%Y-%m-%d %H:%i:%s\') as withdrawal_time,\r\nwithdrawal_amount,\r\nwithdrawal_account,  \r\nDATE_FORMAT(deposit_time, \'%Y-%m-%d %H:%i:%s\') as deposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\nwithdrawal_time desc, withdrawal_amount desc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (571, 347, 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nDATE_FORMAT(withdrawal_time, \'%Y-%m-%d %H:%i:%s\') as withdrawal_time,\r\nwithdrawal_amount,\r\nwithdrawal_account,  \r\nDATE_FORMAT(deposit_time, \'%Y-%m-%d %H:%i:%s\') as deposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\ncarrier_name asc', '4', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (572, 347, 'SELECT \r\ncarrier_name,\r\nbank_card,\r\nDATE_FORMAT(withdrawal_time, \'%Y-%m-%d %H:%i:%s\') as withdrawal_time,\r\nwithdrawal_amount,\r\nwithdrawal_account,  \r\nDATE_FORMAT(deposit_time, \'%Y-%m-%d %H:%i:%s\') as deposit_time,\r\ndeposit_amount,\r\ndeposit_account,\r\ndeposit_number\r\nFROM\r\nads_carrier_bank_statis\r\nORDER BY \r\ncarrier_name asc,deposit_number asc', '4', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (573, 349, 'select \r\noffice_id,\r\noffice_name,\r\nservice_provider,\r\nenterprise_id,\r\nenterprise_name,\r\nreceipt_batch_id,\r\nreceipt_batch_no,\r\nticket_id,\r\ninvoice_no,\r\norder_id,\r\norder_no,\r\ncarrier_id,\r\nname,\r\nidcard_no,\r\nDATE_FORMAT(receipt_confirm_date, \'%Y-%m-%d %H:%i:%s\') as receipt_confirm_date,\r\nDATE_FORMAT(sign_time, \'%Y-%m-%d %H:%i:%s\') as sign_time,\r\nsum_shipper4ticket4amt,\r\nshipper4payed4amt,\r\ncarrier4payed4amt\r\nfrom ads_carrier_receipt_statis\r\norder by idcard_no asc', '2', NULL, NULL, NULL);
INSERT INTO `api_base_log` VALUES (574, 349, 'select \r\noffice_id,\r\noffice_name,\r\nservice_provider,\r\nenterprise_id,\r\nenterprise_name,\r\nreceipt_batch_id,\r\nreceipt_batch_no,\r\nticket_id,\r\ninvoice_no,\r\norder_id,\r\norder_no,\r\ncarrier_id,\r\nname,\r\nidcard_no,\r\nDATE_FORMAT(receipt_confirm_date, \'%Y-%m-%d %H:%i:%s\') as receipt_confirm_date,\r\nDATE_FORMAT(sign_time, \'%Y-%m-%d %H:%i:%s\') as sign_time,\r\nsum_shipper4ticket4amt,\r\nshipper4payed4amt,\r\ncarrier4payed4amt\r\nfrom ads_carrier_receipt_statis\r\norder by idcard_no asc, sum_shipper4ticket4amt desc, shipper4payed4amt desc, carrier4payed4amt desc', '3', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for api_param
-- ----------------------------
DROP TABLE IF EXISTS `api_param`;
CREATE TABLE `api_param`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `api_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '所属接口',
  `param_type` tinyint(3) UNSIGNED NOT NULL COMMENT '参数类型（1：入参；2：出参）',
  `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `field_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字段类型',
  `required` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否必传(1:是；2：否)',
  `remark` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '说明',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_api_id`(`api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5833 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口参数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of api_param
-- ----------------------------
INSERT INTO `api_param` VALUES (5784, 347, 2, 'carrierName', 'String', 1, '司机姓名', NULL, NULL);
INSERT INTO `api_param` VALUES (5785, 347, 2, 'bankCard', 'String', 1, '二级户名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5786, 347, 2, 'withdrawalTime', 'String', 1, '出金时间', NULL, NULL);
INSERT INTO `api_param` VALUES (5787, 347, 2, 'withdrawalAmount', 'Number', 1, '出金金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5788, 347, 2, 'withdrawalAccount', 'String', 1, '出金账户', NULL, NULL);
INSERT INTO `api_param` VALUES (5789, 347, 2, 'depositTime', 'String', 1, '入金时间', NULL, NULL);
INSERT INTO `api_param` VALUES (5790, 347, 2, 'depositAmount', 'Number', 1, '入金金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5791, 347, 2, 'depositAccount', 'String', 1, '入金账户', NULL, NULL);
INSERT INTO `api_param` VALUES (5792, 347, 2, 'depositNumber', 'String', 1, '入金运单号', NULL, NULL);
INSERT INTO `api_param` VALUES (5793, 348, 2, 'enterpriseName', 'String', 1, '托运人名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5794, 348, 2, 'officeName', 'String', 1, '部门', NULL, NULL);
INSERT INTO `api_param` VALUES (5795, 348, 2, 'name', 'String', 1, '司机名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5796, 348, 2, 'idcardNo', 'String', 1, '身份证后六位', NULL, NULL);
INSERT INTO `api_param` VALUES (5797, 348, 2, 'settleTime', 'String', 1, '时间(年度月份)', NULL, NULL);
INSERT INTO `api_param` VALUES (5798, 348, 2, 'carrier4payed4amt', 'Number', 1, '系统成立前运费金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5799, 349, 2, 'officeName', 'String', 1, '业务归属部门', NULL, NULL);
INSERT INTO `api_param` VALUES (5800, 349, 2, 'serviceProvider', 'String', 1, '服务商名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5801, 349, 2, 'enterpriseName', 'String', 1, '托运人名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5802, 349, 2, 'receiptBatchNo', 'String', 1, '开票批次号', NULL, NULL);
INSERT INTO `api_param` VALUES (5803, 349, 2, 'invoiceNo', 'String', 1, '发票号码', NULL, NULL);
INSERT INTO `api_param` VALUES (5804, 349, 2, 'receiptConfirmDate', 'String', 1, '开票日期', NULL, NULL);
INSERT INTO `api_param` VALUES (5805, 349, 2, 'sumShipper4ticket4amt', 'Number', 1, '开票结算金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5806, 349, 2, 'orderNo', 'String', 1, '运单号码', NULL, NULL);
INSERT INTO `api_param` VALUES (5807, 349, 2, 'signTime', 'String', 1, '运单双签时间', NULL, NULL);
INSERT INTO `api_param` VALUES (5808, 349, 2, 'shipper4payed4amt', 'Number', 1, '运单结算金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5809, 349, 2, 'name', 'String', 1, '承运人姓名', NULL, NULL);
INSERT INTO `api_param` VALUES (5810, 349, 2, 'idcardNo', 'String', 1, '承运人身份证号', NULL, NULL);
INSERT INTO `api_param` VALUES (5811, 349, 2, 'carrier4payed4amt', 'Number', 1, '支付司机金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5812, 350, 2, 'internalDept', 'String', 1, '业务归属部门', NULL, NULL);
INSERT INTO `api_param` VALUES (5813, 350, 2, 'enterpriseName', 'String', 1, '托运人名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5814, 350, 2, 'orderNo', 'String', 1, '运单号', NULL, NULL);
INSERT INTO `api_param` VALUES (5815, 350, 2, 'signTime', 'String', 1, '运单双签时间', NULL, NULL);
INSERT INTO `api_param` VALUES (5816, 350, 2, 'orderAmount', 'Number', 1, '运单金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5817, 350, 2, 'carrierName', 'String', 1, '承运人姓名', NULL, NULL);
INSERT INTO `api_param` VALUES (5818, 350, 2, 'carrierIdcard', 'String', 1, '承运人身份证号', NULL, NULL);
INSERT INTO `api_param` VALUES (5819, 350, 2, 'payCarrierAmount', 'Number', 1, '支付承运人金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5820, 350, 2, 'payCarrierTime', 'String', 1, '支付承运人时间', NULL, NULL);
INSERT INTO `api_param` VALUES (5821, 350, 2, 'orderStatus', 'String', 1, '运单状态（取消/关闭）', NULL, NULL);
INSERT INTO `api_param` VALUES (5822, 350, 2, 'orderRefundAmount', 'Number', 1, '运单退款金额', NULL, NULL);
INSERT INTO `api_param` VALUES (5823, 351, 2, 'enterpriseName', 'String', 1, '托运人名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5824, 351, 2, 'officeName', 'String', 1, '业务机构名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5825, 351, 2, 'taxIncludedIncome', 'Number', 1, '含税收入', NULL, NULL);
INSERT INTO `api_param` VALUES (5826, 351, 2, 'taxExcludedIncome', 'Number', 1, '未税收入', NULL, NULL);
INSERT INTO `api_param` VALUES (5827, 351, 2, 'tax', 'Number', 1, '税', NULL, NULL);
INSERT INTO `api_param` VALUES (5828, 351, 2, 'contractLiabilities', 'Number', 1, '合同负债', NULL, NULL);
INSERT INTO `api_param` VALUES (5829, 351, 2, 'receivableFreightDiff', 'Number', 1, '应收运费差额', NULL, NULL);
INSERT INTO `api_param` VALUES (5830, 352, 2, 'enterpriseName', 'String', 1, '托运人名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5831, 352, 2, 'officeName', 'String', 1, '业务机构名称', NULL, NULL);
INSERT INTO `api_param` VALUES (5832, 352, 2, 'carrierFreightAmount', 'Number', 1, '司机运费总额', NULL, NULL);

-- ----------------------------
-- Table structure for datasource_info
-- ----------------------------
DROP TABLE IF EXISTS `datasource_info`;
CREATE TABLE `datasource_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_source_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '数据源名称',
  `db_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据库ip',
  `db_port` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据库端口',
  `db_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号',
  `db_password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `db_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '库名',
  `db_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据源url',
  `db_driver` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'com.mysql.cj.jdbc.Driver' COMMENT '驱动',
  `min_size` smallint(6) NULL DEFAULT NULL COMMENT '最小连接数',
  `max_size` smallint(6) NULL DEFAULT NULL COMMENT '最大连接数',
  `init_size` smallint(6) NULL DEFAULT NULL COMMENT '初始化连接数',
  `type` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '库类型',
  `logic_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除， 0. 正常、1. 删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '数据源配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of datasource_info
-- ----------------------------
INSERT INTO `datasource_info` VALUES (37, 'doris', '192.168.4.240', '19030', 'bd_kcwl_rw', 'gSz/YvCLCPZrd1r/tR9QOKYpI8191I1JJVN9lTKJg/mg/fL71VoN+LCYjrO2tcWGD2PrHV+rS8hxQeKZnnPYgA==', 'mmads', 'jdbc:MYSQL://192.168.4.240:19030/mmads?useUnicode=TRUE&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=TRUE&serverTimezone=GMT%2B8', 'com.mysql.cj.jdbc.Driver', 1, 10, 10, 'MYSQL', 0, '2024-08-11 02:45:49', '2024-09-22 09:58:17');
INSERT INTO `datasource_info` VALUES (38, 'doris（本机测试）', '1.92.74.70', '19030', 'bd_kcwl_rw', 'rxDPyVRbAOjFhTKhirWtWd2exUhz7a+/CoKv0MQiIDtq2gLl9OqWiU4PQGjcAUyNT5TKLXiw2qUj7QP0kZ1pWw==', 'mmads', 'jdbc:MYSQL://1.92.74.70:19030/mmads?useUnicode=TRUE&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=TRUE&serverTimezone=GMT%2B8', 'com.mysql.cj.jdbc.Driver', 1, 10, 10, 'MYSQL', 1, '2024-09-22 13:44:22', '2024-09-22 13:44:50');
INSERT INTO `datasource_info` VALUES (39, '自测（Doris）', '124.70.87.36', '19030', 'bd_kcwl_rw', 'nKpsKnKRN8ooNtOw6JsGYnjFoNkymfcZOQ/AGDHfvuoongOVnPQ7+6tx8LJKJZyCZ6wbbOqRJrTtT5B+/n2ibQ==', 'mmads', 'jdbc:MYSQL://124.70.87.36:19030/mmads?useUnicode=TRUE&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=TRUE&serverTimezone=GMT%2B8', 'com.mysql.cj.jdbc.Driver', 1, 10, 10, 'MYSQL', 0, '2024-09-22 13:54:39', NULL);

SET FOREIGN_KEY_CHECKS = 1;
