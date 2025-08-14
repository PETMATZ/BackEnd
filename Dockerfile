# 1단계: 빌드 환경 설정
FROM gradle:8.4.0-jdk17-alpine AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle build -x test --no-daemon

# 2단계: 실행 환경 설정
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/petmetz-0.0.1-SNAPSHOT.jar petmatz.jar
ENTRYPOINT ["java", "-jar", "petmatz.jar"]