FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

RUN groupadd --gid 1001 springgroup && \
    useradd --uid 1001 --gid springgroup --shell /bin/bash --create-home spring

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

RUN chown -R spring:springgroup /app

USER spring

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
