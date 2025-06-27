一、Overview：简介
本系统是一个药品供应链及处方执行的系统。主要包含药品管理、患者管理、药房管理、药房药品分配、处方创建和执行流程等

二、Features：核心功能
1, 患者信息管理：新增患者，更新患者，删除患者，查询患者
2, 药品信息管理：
3, 药房信息管理：
4, 给药房分配药品：
5, 处方流程管理：
6, 审计日志：记录每一次处方创建、处方执行的日志信息，以及检索日志

三、Installation：安装说明
1, JDK17+  Maven3.6+  Mysql8.0+(推荐Mysql9.3)  Git
2, 克隆项目： git clone https://github.com/jing747556248/pharma.git
3, 构建项目： mvn clean install
4, 初始化数据库：执行数据库初始化sql文件-pharma-core/src/main/resources/sql/pharma-init.sql, 
修改application.yaml中数据库连接信息为自己的
5, 运行项目：项目为springboot项目，通过IDE导入项目运行 
或者将项目打成jar包， 命令行运行： java -jar {your_target_directory}\pharma-core-1.0.0.jar

四、API Documentation：API接口说明
1, swagger地址：http://localhost:8081/swagger-ui/index.html
2, 采用Elide框架自动生成基本的CRUD接口
3, 自动义了部分接口，例如：根据药房ID查合同药品，创建处方，执行处方

五、Systems Design：系统设计
1, ER图：pharma-core/src/main/resources/DB-ER/ER图.png
2, 引入低代码框架Elide，省去了部分Controller和Service类
3, 所有接口符合JSON:API风格，定义了统一的返回对象，统一的业务异常类BizException，异常错误码枚举类，并且将这些公共对象独立成pharma-common包
4, 采用数据库乐观锁控制处方的并发执行，保证药品库存更新正确，已经处方正确执行
5, 采用spring的Event事件机制，记录审计日志
