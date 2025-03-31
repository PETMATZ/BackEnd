# OpenJDK 17 사용
FROM openjdk:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 컨테이너 내부로 복사
COPY build/libs/petmetz-0.0.1-SNAPSHOT.jar petmatz.jar


# 컨테이너 실행 시 실행할 명령어
CMD ["java", "-jar", "petmatz.jar"]