# Stage 1: Build - Maven ile projeyi derle
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Maven Wrapper dosyalarını kopyala
COPY pom.xml mvnw ./
COPY .mvn .mvn

# mvnw dosyasına çalıştırma izni ver ve bağımlılıkları indir
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Proje dosyalarını kopyala ve derle
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run - Çalışma ortamı
FROM openjdk:17-jdk-slim
WORKDIR /app

# Derleme aşamasından oluşan JAR dosyasını kopyala
COPY --from=build /app/target/terapinisec-0.0.1-SNAPSHOT.jar app.jar

# Health check ve port ayarları
ENV HEALTH_CHECK_PATH="/actuator/health"
ENV SERVER_PORT=8080
EXPOSE 8080

# Uygulamayı çalıştır (ENTRYPOINT Dockerfile'da belirtilen komutu Render'ın varsayılan start komutunun üzerine yazar)
ENTRYPOINT ["java", "-jar", "app.jar"]
