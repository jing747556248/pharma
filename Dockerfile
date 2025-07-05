# 第一阶段：构建阶段
FROM maven:3.8-jdk-17 AS builder
WORKDIR /app
COPY pom.xml .
# 先下载依赖(利用Docker层缓存)
RUN mvn dependency:go-offline
COPY src ./src
# 执行构建
RUN mvn clean package -DskipTests


# 第二阶段：运行阶段
FROM openjdk:17-jre-alpine
WORKDIR /app
# 从构建阶段复制jar包
COPY --from=build /app/target/*.jar app.jar

# 暴露端口(根据你的应用调整)
EXPOSE 8081

# 设置JVM参数(可选)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]