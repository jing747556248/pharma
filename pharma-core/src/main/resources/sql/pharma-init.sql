CREATE DATABASE `pharma` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

DROP TABLE IF EXISTS `audit_log`;
CREATE TABLE `audit_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `prescription_id` bigint NOT NULL COMMENT '处方ID',
  `patient_id` bigint DEFAULT NULL COMMENT '患者ID',
  `pharmacy_id` bigint NOT NULL COMMENT '药房ID',
  `required_drugs` json DEFAULT NULL COMMENT '所需药品JSON数组',
  `dispensed_status` int DEFAULT NULL COMMENT '配药状态(1:配药成功,2:配药失败)',
  `failure_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_prescription_id` (`prescription_id`),
  KEY `idx_patient_id` (`patient_id`),
  KEY `idx_pharmacy_id` (`pharmacy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='处方审计日志表';


DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '药物名称',
  `manufacturer` varchar(200) DEFAULT NULL COMMENT '制造商',
  `batch_number` varchar(50) DEFAULT NULL COMMENT '批号',
  `production_date` varchar(50) DEFAULT NULL COMMENT '生产日期',
  `expiry_date` varchar(50) DEFAULT NULL COMMENT '有效期至',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '单价(元)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_batch` (`batch_number`),
  KEY `idx_expiry` (`expiry_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药物信息表';


DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) DEFAULT NULL COMMENT '患者姓名',
  `age` int DEFAULT NULL COMMENT '患者年龄',
  `gender` int DEFAULT NULL COMMENT '性别(0:未知,1:男,2:女)',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='患者信息表';


DROP TABLE IF EXISTS `pharmacy`;
CREATE TABLE `pharmacy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '药房名称',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详细地址',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `business_hours` varchar(50) DEFAULT NULL COMMENT '营业时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药房信息表';


DROP TABLE IF EXISTS `pharmacy_drug_relationship`;
CREATE TABLE `pharmacy_drug_relationship` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pharmacy_id` bigint NOT NULL COMMENT '药房ID',
  `drug_id` bigint NOT NULL COMMENT '药品ID',
  `stock` int NOT NULL DEFAULT '0' COMMENT '该药房库存量',
  `version` int NOT NULL DEFAULT '0' COMMENT '数据版本，乐观锁控制并发更新',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_pharmacy_id` (`pharmacy_id`),
  KEY `idx_drug_id` (`drug_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药房-药品关联表';


DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pharmacy_id` bigint NOT NULL COMMENT '所属药房ID',
  `patient_id` bigint DEFAULT NULL COMMENT '患者id',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态(0:未执行,1:配药成功,2:配药失败)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_pharmacy_id` (`pharmacy_id`),
  KEY `idx_patient_name` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='处方主表';


DROP TABLE IF EXISTS `prescription_item`;
CREATE TABLE `prescription_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `prescription_id` bigint NOT NULL COMMENT '处方ID',
  `drug_id` bigint NOT NULL COMMENT '药品ID',
  `quantity` int NOT NULL COMMENT '数量',
  `dosage` varchar(100) NOT NULL COMMENT '用法用量(如:每日2次,每次1片)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最近修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_prescription_id` (`prescription_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='处方明细表';

