CREATE SCHEMA IF NOT EXISTS `cd_eapa` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `eapa` ;
/*用户表*/
CREATE TABLE IF NOT EXISTS `ep_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `user_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录用户名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录密码',
  `real_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ep_user_name_index` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目开发人员信息表';

/*项目表*/
CREATE TABLE IF NOT EXISTS `ep_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id唯一标识' DEFAULT 1000,
  `project_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `to_emails` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件接收者邮件地址',
  `to_phones` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '短信接受者号码',
  `warn_exception` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报警异常类型',
  `status` tinyint(4) DEFAULT '0' COMMENT '账户状态(0-上线,1-下线)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ep_project_project_id` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目信息表';

/*异常信息表*/
CREATE TABLE IF NOT EXISTS `ep_exception_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exception_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '异常名称',
  `occur_time` bigint(20) NOT NULL COMMENT '发生时间',
  `parameter` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法参数',
  `module` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块名称',
  `handler_class` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发生的异常所在的类',
  `error_msg` text COLLATE utf8mb4_unicode_ci COMMENT '详细信息',
  `uri` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求的路径',
  `headers` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求头部信息',
  `request_method` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求的方法',
  `ip` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求的服务ip',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `handler_method` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `handler_line` int(20) DEFAULT NULL,
  `environment` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发生异常的环境',
  `extra_information` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `ep_exception_project_id` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='异常记录表';

CREATE TABLE IF NOT EXISTS `ep_system_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `project_id` bigint(20) NOT NULL COMMENT '项目id唯一标识',
    `cpu_usage` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'cpu使用率',
    `mem_usage` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内存使用率',
    `partition_usage` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分区使用率',
    `network_speed` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '网络速率',
    `harddisk_io_speed` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '磁盘IO速率',
    `collect_time` BIGINT(20) NOT NULL COMMENT '收集时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ep_project_project_id` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统信息收集表';