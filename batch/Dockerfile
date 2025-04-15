# 1단계: build
FROM gradle:8.13-jdk21 AS builder
WORKDIR /app

COPY . .

RUN gradle :batch:clean :batch:bootJar -x test

# 2단계: 실행용 이미지
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/batch/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
