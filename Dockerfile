# 1. OpenJDK 17 kullan
FROM openjdk:17-jdk-slim AS build

# 2. Çalışma dizinini ayarla
WORKDIR /app

# 3. Maven proje dosyalarını kopyala ve bağımlılıkları indir
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# 4. Proje dosyalarını kopyala ve build et
COPY src ./src
RUN ./mvnw clean package -DskipTests

# 5. Son imaj oluştur (runtime için)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 6. Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "/app.jar"]
