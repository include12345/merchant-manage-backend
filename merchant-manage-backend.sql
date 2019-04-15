-- 1.创建数据库
create schema if not exists `business` default character set utf8mb4 collate utf8mb4_bin;


-- -----------------------------------------------------
-- Table `business`.`merchant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `business`.`merchant` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
  `sn` VARCHAR(36) NOT NULL COMMENT '商户sn',
  `name` VARCHAR(128) NOT NULL COMMENT '商户名',
  `cellphone` VARCHAR(36) NOT NULL COMMENT '部门id',
  `ctime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `mtime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '',
  `version` BIGINT(20) UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`) COMMENT '')
  ENGINE = InnoDB
  COMMENT = '商户基本信息表';

-- -----------------------------------------------------
-- Table `business`.`consumer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `business`.`consumer` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
  `sn` VARCHAR(36) NOT NULL COMMENT '会员sn',
  `name` VARCHAR(128) NOT NULL COMMENT '会员名称',
  `cellphone` VARCHAR(36) NOT NULL COMMENT '会员手机号',
  `email` VARCHAR(36) NOT NULL COMMENT '会员email',
  `wechat` VARCHAR(36) NOT NULL COMMENT '微信',
  `ctime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `mtime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '',
  `version` BIGINT(20) UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`) COMMENT '')
  ENGINE = InnoDB
  COMMENT = '会员基本信息表';


-- -----------------------------------------------------
-- Table `business`.`merchant_consumer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `business`.`merchant_consumer` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
  `merchant_id` VARCHAR(36) NOT NULL COMMENT '商户id',
  `consumer_id` VARCHAR(36) NOT NULL COMMENT '会员id',
  `ctime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `mtime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '',
  `version` BIGINT(20) UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`) COMMENT '',
  KEY `idx_merchant_id` (`merchant_id`) USING BTREE,
  KEY `idx_consumer_id` (`consumer_id`) USING BTREE,
  UNIQUE INDEX `merchant_id_consumer_id` (`merchant_id`,`consumer_id`))
  ENGINE = InnoDB
  COMMENT = '商户会员关系表';

-- -----------------------------------------------------
-- Table `business`.`merchant_consumer_wallet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `business`.`merchant_consumer_wallet` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
  `merchant_id` VARCHAR(36) NOT NULL COMMENT '商户id',
  `consumer_id` VARCHAR(36) NOT NULL COMMENT '会员id',
  `balance` BIGINT(20) NULL DEFAULT '0' COMMENT '会员余额',
  `ctime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `mtime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '',
  `version` BIGINT(20) UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`) COMMENT '',
  KEY `idx_merchant_id` (`merchant_id`) USING BTREE,
  KEY `idx_consumer_id` (`consumer_id`) USING BTREE,
  UNIQUE INDEX `merchant_id_consumer_id` (`merchant_id`,`consumer_id`))
  ENGINE = InnoDB
  COMMENT = '商户会员钱包';

-- -----------------------------------------------------
-- Table `business`.`wallet_add_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `business`.`wallet_add_transaction` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
  `wallet_id` VARCHAR(36) NOT NULL COMMENT '商户会员钱包id',
  `before_balance` BIGINT(20) NULL DEFAULT '0' '变更前金额',
  `add_amount` BIGINT(20) NULL DEFAULT '0' COMMENT '会员充值金额',
  `after_balance` BIGINT(20) NULL DEFAULT '0' COMMENT '变更后余额',
  `ctime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `mtime` BIGINT(20) NULL DEFAULT NULL COMMENT '',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '',
  `version` BIGINT(20) UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`) COMMENT '',
  KEY `idx_wallet_id` (`wallet_id`) USING BTREE)
  ENGINE = InnoDB
  COMMENT = '商户会员充值记录';

-- -----------------------------------------------------
-- `business`.`transaction`
-- -----------------------------------------------------
create table if not exists `business`.`transaction`(
  `id` varchar(37) not null comment 'UUID',
  `tsn` varchar(32) not null comment '订单流水号',
  `order_sn` varchar(32) not null comment '订单号',
  `name` varchar(128) not null comment '流水描述',
  `remark` varchar(128) null comment '流水备注',
  `original_amount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '原始金额',
  `pay_amount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '实付金额',
  `discount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '优惠金额',
  `merchant_id` VARCHAR(36) NOT NULL COMMENT '商户id',
  `consumer_id` VARCHAR(36) NOT NULL COMMENT '会员id',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '流水类型 1:支付 2:退款 3:部分退款',
  `status` int(10) unsigned null default null comment '流水状态 1000:待支付 1001:支付中 1002:支付成功 1003:支付失败 1004:支付异常 1005:退款成功 1006:退款失败 1007:退款异常',
  `ctime` bigint(20) null default null comment '',
  `mtime` bigint(20) null default null comment '',
  `deleted` tinyint(1) not null default '0' comment '',
  primary key (`id`) comment '',
  unique index `idx_tsn` ('tsn' asc) comment '',
    index `order_sn` ('order_sn' asc) comment ''
)engine=InnoDB
  comment='交易流水表';

-- -----------------------------------------------------
-- `business`.`transaction`
-- -----------------------------------------------------
create table if not exists `business`.`order`(
  `id` varchar(37) not null comment 'UUID',
  `sn` varchar(32) not null comment '订单号',
  `name` varchar(128) not null comment '流水描述',
  `remark` varchar(128) null comment '流水备注',
  `original_amount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '原始金额',
  `pay_amount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '实付金额',
  `discount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '优惠金额',
  `merchant_id` VARCHAR(36) NOT NULL COMMENT '商户id',
  `consumer_id` VARCHAR(36) NOT NULL COMMENT '会员id',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '流水类型 1:支付 2:退款 3:部分退款',
  `status` int(10) unsigned null default null comment '订单状态 0:待支付 1:支付中 2:支付成功 3:支付失败 4:支付异常 5:退款成功 6:退款失败 7:退款异常',
  `ctime` bigint(20) null default null comment '',
  `mtime` bigint(20) null default null comment '',
  `deleted` tinyint(1) not null default '0' comment '',
  primary key (`id`) comment '',
  unique index `idx_sn` ('sn' asc) comment ''
)engine=InnoDB
  comment='交易流水表';