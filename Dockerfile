# syntax=docker/dockerfile:1

# Build stage: compiles, tests and packages the app with its runtime libs.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B verify

# Runtime stage: JRE only, non-root user.
FROM eclipse-temurin:21-jre
RUN groupadd --system app && useradd --system --gid app app
WORKDIR /app
COPY --from=build /workspace/target/lib ./lib
COPY --from=build /workspace/target/poc-dependabot-test-*.jar ./app.jar
USER app
EXPOSE 8080
ENV PORT=8080
HEALTHCHECK --interval=30s --timeout=3s CMD ["sh", "-c", "wget -qO- http://localhost:${PORT}/health || exit 1"]
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
