# syntax=docker/dockerfile:1

### First we build the application in docker in a controlled env
FROM maven:3.8.3-eclipse-temurin-11 as build
WORKDIR /app
COPY ./ /app/
RUN --mount=type=cache,id=mvncache,target=/root/.m2/repository,rw \
	mvn -B clean package

### Then we add the builded war to a JEE server.
FROM payara/server-full:5.2021.9-jdk11
ARG STORE_PASSWORD=changeit
## Download the JDBC driver
USER root
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/* && \
    wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar \
         -O /tmp/h2.jar 
USER payara
## This script adds a jdbc connection pool and ressource
COPY docker/post-boot-commands.asadmin $POSTBOOT_COMMANDS
##

## We copy the OUR certificates to payara
COPY --from=build /app/utils/src/main/resources/mycert.crt  /app/utils/src/main/resources/mycert.p12 /tmp/
COPY --from=build /app/utils/src/main/resources/mycert-pub.p12  /app/utils/src/main/resources/mycert-pub.p12 /

RUN keytool -importkeystore -noprompt -destkeystore /opt/payara/appserver/glassfish/domains/domain1/config/keystore.jks -srckeystore /tmp/mycert.p12 -srcstoretype PKCS12 -alias mycert -srcstorepass storepass -deststorepass ${STORE_PASSWORD} -deststoretype pkcs12 && \
    keytool -importcert -noprompt -trustcacerts -destkeystore /opt/payara/appserver/glassfish/domains/domain1/config/cacerts.jks -file /tmp/mycert.crt -alias mycert -srcstorepass storepass -deststorepass ${STORE_PASSWORD} -deststoretype pkcs12

# COPY target/*.war $DEPLOY_DIR
#COPY --from=build /app/restApp/target/*.war \
#                  $DEPLOY_DIR
#COPY --from=build /app/wsApp/target/*.war \
#                  $DEPLOY_DIR
