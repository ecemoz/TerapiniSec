# 1. OpenJDK 17 kullan
FROM openjdk:17-jdk-slim

# 2. Çalışma dizinini belirle
WORKDIR /app

# 3. Maven Wrapper dosyalarını kopyala
COPY pom.xml mvnw ./
COPY .mvn .mvn

# 4. mvnw dosyasına çalıştırma izni ver
RUN chmod +x mvnw

# 5. Bağımlılıkları indir
RUN ./mvnw dependency:go-offline

# 6. Proje dosyalarını kopyala
COPY . .

# 7. **Tekrar chmod +x ekleyelim (Önlem için)**
RUN chmod +x mvnw

# 8. Maven ile projeyi derle
RUN ./mvnw clean package -DskipTests

# 9. Sonuçta oluşan jar dosyasını kullan
CMD ["java", "-jar", "target/terapinisec.jar"]
