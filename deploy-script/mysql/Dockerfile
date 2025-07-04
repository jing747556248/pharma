FROM mysql:8.0

# 设置时区(可选)
ENV TZ=Asia/Shanghai

# 复制初始化脚本
COPY pharma-init.sql /docker-entrypoint-initdb.d/

# 复制自定义配置文件(可选)
#COPY my.cnf /etc/mysql/conf.d/

# 设置环境变量
ENV MYSQL_DATABASE=pharma
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root