FROM alpine:latest

RUN apk update && apk add --no-cache \
  openjdk21 \
  maven \
  bash \ 
  curl

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app

COPY . . 

RUN mvn clean install -DskipTests=true

EXPOSE 8080

CMD ["java", "-jar", "target/financecontrol.jar"]
