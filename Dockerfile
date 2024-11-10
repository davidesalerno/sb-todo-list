FROM registry.access.redhat.com/ubi8/openjdk-17 AS builder

RUN mkdir app

WORKDIR ./app

COPY pom.xml .
COPY src src

RUN mvn -B -f pom.xml clean package -DskipTests

# lightweight image
FROM registry.access.redhat.com/ubi8/openjdk-17-runtime

COPY --from=builder /home/jboss/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]