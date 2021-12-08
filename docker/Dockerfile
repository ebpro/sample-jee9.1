# syntax=docker/dockerfile:1.3

### First we build the application in docker in a controlled env
FROM maven:3.8.4-eclipse-temurin-11 as build
WORKDIR /app
# COPY pom.xml /app/pom.xml
COPY . /app/

RUN --mount=type=cache,id=mvncache,target=/root/.m2/repository,rw \
	mvn -B package

### Then we add the builded war to a JEE server.
#FROM payara/server-full:5.2021.9-jdk11 as server
FROM brunoe/payara:jdk11-6 as server

ARG STORE_PASSWORD=changeit
## Download the JDBC driver
USER root
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/* && \
    wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar \
         -O /tmp/h2.jar && \
    cp /tmp/h2.jar /opt/payara/appserver/glassfish/domains/domain1/lib/h2.jar

USER payara
## This script adds a jdbc connection pool and ressource
COPY docker/post-boot-commands.asadmin $POSTBOOT_COMMANDS
##

## We copy the OUR certificates to payara
COPY --from=build /app/utils/src/main/resources/mycert.crt  /app/utils/src/main/resources/mycert.p12 /tmp/
COPY --from=build /app/utils/src/main/resources/mycert-pub.p12  /app/utils/src/main/resources/mycert-pub.p12 /

RUN keytool -importkeystore -noprompt -destkeystore /opt/payara/appserver/glassfish/domains/domain1/config/keystore.jks -srckeystore /tmp/mycert.p12 -srcstoretype PKCS12 -alias mycert -srcstorepass storepass -deststorepass ${STORE_PASSWORD} -deststoretype pkcs12 && \
    keytool -importcert -noprompt -trustcacerts -destkeystore /opt/payara/appserver/glassfish/domains/domain1/config/cacerts.jks -file /tmp/mycert.crt -alias mycert -srcstorepass storepass -deststorepass ${STORE_PASSWORD} -deststoretype pkcs12

#RUN wget https://github.com/eclipse-ee4j/mojarra/releases/download/3.0.2-RELEASE/jakarta.faces-3.0.2.jar \
#    -O /opt/payara/appserver/glassfish/modules/jakarta.faces.jar
#RUN wget https://github.com/eclipse-ee4j/mojarra/releases/download/2.3.17-RELEASE/jakarta.faces-2.3.17.jar \
#    -O /opt/payara/appserver/glassfish/modules/jakarta.faces.jar
FROM server as production
#COPY --from=build /app/jee/restApp/target/*.war \
#                  $DEPLOY_DIR
#COPY --from=build /app/jee/wsApp/target/*.war \
#                  $DEPLOY_DIR
#COPY --from=build /app/jee/jsf/target/*.war \
#                  $DEPLOY_DIR
COPY --from=build /app/jee/ear/target/*.ear \
                  $DEPLOY_DIR