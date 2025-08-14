# ---- 1단계: 빌드 (JDK 17, 멀티아치)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Gradle Wrapper/설정 먼저 복사 (의존성 캐시)
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle* build.gradle* gradle.properties* ./

RUN chmod +x gradlew
RUN ./gradlew --no-daemon --version

# 소스 복사 후 빌드
COPY src ./src
RUN ./gradlew clean bootJar -x test --no-daemon

# 2단계: 실행 환경 설정
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/petmetz-0.0.1-SNAPSHOT.jar petmatz.jar
ENTRYPOINT ["java", "-jar", "petmatz.jar"]