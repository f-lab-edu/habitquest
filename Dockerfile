# 1단계: 빌드용 이미지 (Gradle 8.13 + Java 21)
FROM gradle:8.13-jdk21 AS builder
WORKDIR /app

# 전체 프로젝트 복사 (settings.gradle 포함)
COPY . .

# 실행 모듈(api)만 빌드 (테스트 생략)
RUN gradle :api:clean :api:build -x test

# 2단계: 실행용 이미지 (Java 21)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# 빌드 결과 복사
COPY --from=builder /app/api/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
