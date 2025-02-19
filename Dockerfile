# 1. OpenJDK 17 kullan
FROM openjdk:17-jdk-slim

# 2. Çalışma dizinini belirle
WORKDIR /app

# 3. Maven Wrapper dosyalarını kopyala
COPY pom.xml mvnw ./
COPY .mvn .mvn

# 4. mvnw dosyasına çalıştırma izni ver
RUN chmod +x mvnw

# 5. Bağımlılıkları önceden indir (Cache için)
RUN ./mvnw dependency:go-offline

# 6. Proje dosyalarını kopyala
COPY . .

# 7. Tekrar mvnw çalıştırma izni ver (Önlem için)
RUN chmod +x mvnw

# 8. Maven ile projeyi derle
RUN ./mvnw clean package -DskipTests

# 9. Çalışacak JAR dosyasını belirle
ARG JAR_FILE=target/terapinisec-0.0.1-SNAPSHOT.jar
RUN cp ${JAR_FILE} app.jar

# 10. Health check için ENV değişkeni ekle
ENV HEALTH_CHECK_PATH="/actuator/health"
ENV SERVER_PORT=8080

# 11. Portu dışa aç
EXPOSE 8080

# 12. Çalıştırma komutu
CMD ["java", "-jar", "/app.jar"]
