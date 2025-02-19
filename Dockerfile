# --- Build Aşaması ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Proje bağımlılıklarını önceden indiriyoruz
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Kaynak kodları kopyalayıp uygulamayı derliyoruz
COPY src ./src
RUN mvn clean package -DskipTests

# --- Çalıştırma Aşaması ---
FROM openjdk:17-jdk-alpine
WORKDIR /app
# Derleme aşamasından üretilen JAR dosyasını kopyalıyoruz
COPY --from=build /app/target/*.jar app.jar
# Render, default olarak 8080 portunu kullanır
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
