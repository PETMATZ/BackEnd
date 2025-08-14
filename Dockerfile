# 1단계: 빌드 환경 설정
FROM gradle:8.7.0-jdk17 AS builder
WORKDIR /app

# 1) 의존성 캐시 레이어 (kts/groovy/props 모두 커버)
COPY settings.gradle* build.gradle* gradle.properties* ./
RUN gradle --no-daemon --version

# 2) 소스만 복사
RUN rm -rf /app/src
COPY src ./src

# 3) 클린 빌드 (Spring Boot면 bootJar 권장)
RUN gradle clean bootJar -x test --no-daemon

# 2단계: 실행 환경 설정
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/petmetz-0.0.1-SNAPSHOT.jar petmatz.jar
ENTRYPOINT ["java", "-jar", "petmatz.jar"]