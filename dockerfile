FROM azul/zulu-openjdk:19.0.2

WORKDIR /app

COPY build/libs/wanted-pre-onboarding-0.0.1-SNAPSHOT.jar /app/webApp.jar

ENTRYPOINT ["java", "-jar", "/app/webApp.jar"]
