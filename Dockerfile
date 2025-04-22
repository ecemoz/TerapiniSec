# 1️Maven ile build aşaması
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests && ls -lah target/

# 2️OpenJDK ile çalıştırma aşaması
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Jar'ı /app/target dizinine kopyalıyoruz
RUN mkdir -p /app/target
COPY --from=build /app/target/terapinisec-0.0.1-SNAPSHOT.jar /app/target/terapinisec-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/target/terapinisec-0.0.1-SNAPSHOT.jar"]