FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app
COPY pom.xml .
COPY checkstyle-base.xml .
COPY pmd-reglas-de-codigo.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-jdk
ENV JETTY_VERSION=9.4.56.v20240826
ENV JETTY_HOME=/opt/jetty
RUN apt-get update && apt-get install -y wget && \
    mkdir -p $JETTY_HOME && \
    wget https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/${JETTY_VERSION}/jetty-distribution-${JETTY_VERSION}.tar.gz && \
    tar -xzvf jetty-distribution-${JETTY_VERSION}.tar.gz -C $JETTY_HOME --strip-components=1 && \
    rm jetty-distribution-${JETTY_VERSION}.tar.gz
COPY --from=build /app/target/tallerwebi-base-1.0-SNAPSHOT.war $JETTY_HOME/webapps/spring.war
EXPOSE 8080
WORKDIR $JETTY_HOME
CMD ["java", "-jar", "./start.jar", "jetty.port=8080"]
