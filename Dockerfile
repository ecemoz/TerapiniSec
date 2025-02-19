# 1. OpenJDK 17 kullan
FROM openjdk:17-jdk-slim

# 2. Çalışma dizinini belirle
WORKDIR /app

# 3. JAR dosyasını Docker imajına kopyala
COPY target/terapinisec-0.0.1-SNAPSHOT.jar app.jar

# 4. Çalıştırma komutu
CMD ["java", "-jar", "/app.jar"]
