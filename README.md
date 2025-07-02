# Project Description

A system for drug supply chain and prescription execution based on Spring Boot.

# Core Features

1. Patient information management
2. Drug information management
3. Pharmacy information management
4. Dispensed drugs to pharmacies
5. Create and fulfill prescriptions
6. Prescription fulfill audit log

# Environment and Installation

## Preconditions

- JDK17+
- Maven3.6+
- Mysql8.0+
- Git

## Quick Start

- Cloning project

```
git clone https://github.com/jing747556248/pharma.git
```

- Building project

```
mvn clean install
```

- Initialize the database

> ./pharma-core/src/main/resources/sql/pharma-init.sql

- Running the project

```
java -jar {your_target_directory}\pharma-core-1.0.0.jar
```

# Project Structure

├─pharma-common  									# common公共包
│  └─src
│      └─main
│          └─java
│              └─com.sanofi.pharma.common
│                              ├─dto								# API统一返回DTO
│                              └─exception					# 全局异常拦截
└─pharma-core  											# 核心业务包
    └─src
        ├─main
        │  ├─java
        │  │  └─com.sanofi.pharma.core	# 主体包
        │  │                  ├─config			# configuration配置
        │  │                  ├─constant	   # 系统常量
        │  │                  ├─controller	 # API层
        │  │                  ├─dto				# 业务DTO
        │  │                  ├─entity			# 实体类
        │  │                  │  └─check		# permission check
        │  │                  ├─enums		  # 枚举
        │  │                  ├─event			# 事件
        │  │                  ├─repository	# 数据访问层
        │  │                  │  └─Specification	# JPA SPE查询
        │  │                  ├─service			# 业务接口
        │  │                  │  └─impl			# 业务接口Impl
        │  │                  ├─util				  # 工具类
        │  │                  └─vo					# API返回vo
        │  └─resources							#资源文件
        │      ├─DB-ER
        │      └─sql									# SQL文件
        └─test/										# 单元测试



# Usage Guide

## API documentation

- Swagger address

http://localhost:8081/swagger-ui/index.html

- API Operation Example

```bash
curl -X 'POST' \
  'http://localhost:8081/api/drug' \
  -H 'accept: application/vnd.api+json' \
  -H 'Content-Type: application/vnd.api+json' \
  -d '
{
  "data": {
    "type": "drug",
    "attributes": {
      "batchNumber": "N20250618",
      "createBy": "admin",
      "expiryDate": "2026-10-31",
      "isDeleted": 0,
      "manufacturer": "盼盼制药",
      "name": "塞益灵",
      "price": null,
      "productionDate": "2025-06-18",
      "stock": 1000,
      "updateBy": "admin"
    }
  }
}'

{
  "data": {
    "type": "drug",
    "id": "4",
    "attributes": {
      "batchNumber": "N20250618",
      "createBy": "admin",
      "createTime": null,
      "expiryDate": "2026-10-31",
      "isDeleted": false,
      "manufacturer": "盼盼制药",
      "name": "塞益灵",
      "price": null,
      "productionDate": "2025-06-18",
      "stock": 1000,
      "updateBy": "admin",
      "updateTime": null
    },
    "links": {
      "self": "http://localhost:8081/api/drug/4"
    }
  }
}
```

## Database Table Diagram

> ./pharma-core/src/main/resources/DB-ER/Table-Diagram.png

![Table-Diagram](D:\sanofi\code\pharma\pharma-core\src\main\resources\DB-ER\Table-Diagram.png)



