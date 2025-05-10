# Sử dụng openjdk image để chạy Spring Boot
FROM openjdk:17-jdk-slim

# Tạo thư mục /app trong container
WORKDIR /app

# Copy file JAR của Spring Boot vào container
COPY target/plantstore-0.0.1-SNAPSHOT.jar app.jar


# Cấu hình cổng mà ứng dụng Spring Boot sẽ chạy
EXPOSE 8080

# Lệnh chạy Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
