# 第一阶段：构建应用
FROM eclipse-temurin:17.0.12_7-jre
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：运行应用
#FROM openjdk:11-jre-slim
#WORKDIR /app
COPY --from=build /app/target/pharma-core-1.0.0.jar ./app.jar

# 暴露端口(根据你的应用调整)
EXPOSE 8081

# 设置JVM参数(可选)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]