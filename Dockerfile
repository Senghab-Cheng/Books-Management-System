FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY demo ./demo

WORKDIR /app/demo
RUN chmod +x gradlew && ./gradlew clean bootJar -x check -x test && \
    cp "$(ls -1 build/libs/*.jar | grep -v plain | head -n 1)" /app/app.jar

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/app.jar /app/app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT} -jar /app/app.jar"]
