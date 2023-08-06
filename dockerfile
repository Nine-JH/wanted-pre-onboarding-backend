FROM azul/zulu-openjdk:19.0.2

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew

RUN ./gradlew clean build -x test

COPY build/libs/wanted-pre-onboarding-0.0.1-SNAPSHOT.jar /app/webApp.jar

ENTRYPOINT ["java", "-jar", "/app/webApp.jar"]
