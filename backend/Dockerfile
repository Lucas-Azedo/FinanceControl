#Step 1 - Build
FROM maven:3.9.6-eclipse-temurin-21-alpine as builder

WORKDIR /app

COPY . . 

RUN mvn clean install -DskipTests


#Step 2 - runtime
FROM eclipse-temurin:21-alpine

WORKDIR /app

COPY --from=builder /app/target/financecontrol.jar financecontrol.jar

EXPOSE 8080

CMD ["java", "-jar", "financecontrol.jar"]
