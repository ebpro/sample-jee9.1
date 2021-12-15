### First we build the application in docker in a controlled env
FROM maven:3.8.4-eclipse-temurin-11 as build
ARG GITHUBLOGIN
ARG GITHUBPASSWORD
ARG SONAR_HOST
ARG SONAR_TOKEN

WORKDIR /app

#see https://www.jrebel.com/blog/how-to-speed-up-your-maven-build
# see https://www.baeldung.com/jvm-tiered-compilation
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
#ENV MAVEN_CLI_OPTS="-T 1C --settings /usr/share/maven/ref/settings-docker.xml -B"
ENV MAVEN_CLI_OPTS="--settings /usr/share/maven/ref/settings-docker.xml -B"

COPY docker/settings-docker.xml /usr/share/maven/ref/

# COPY pom.xml /app/pom.xml
COPY . /app/

RUN --mount=type=cache,id=mvncache,target=/root/.m2/repository,rw \
	mvn $MAVEN_CLI_OPTS -Dmaven.test.skip=true verify

RUN --mount=type=cache,id=mvncache,target=/root/.m2/repository,rw \
    if [ "$SONAR_TOKEN" ] ; then \
      mvn $MAVEN_CLI_OPTS -D sonar.branch.name=${GITHUB_REF#refs/heads/} \
        --activate-profiles sonar sonar:sonar && \
      mvn $MAVEN_CLI_OPTS --activate-profiles sonar -pl . sonar-quality-gate:check ; \
    fi

### Then we add the builded war to a JEE server.
#FROM payara/server-full:5.2021.9-jdk11 as server
FROM  brunoe/payara:jdk11-13 as server

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
COPY --chown=payara:payara docker/post-boot-commands.asadmin $POSTBOOT_COMMANDS
##

## We copy the OUR certificates to payara
COPY --from=build --chown=payara:payara /app/utils/src/main/resources/mycert.crt  /app/utils/src/main/resources/mycert.p12 /tmp/
COPY --from=build --chown=payara:payara /app/utils/src/main/resources/mycert-pub.p12  /app/utils/src/main/resources/mycert-pub.p12 /

RUN keytool -importkeystore -noprompt -destkeystore /opt/payara/appserver/glassfish/domains/domain1/config/keystore.jks -srckeystore /tmp/mycert.p12 -srcstoretype PKCS12 -alias mycert -srcstorepass storepass -deststorepass ${STORE_PASSWORD} -deststoretype pkcs12 && \
    keytool -importcert -noprompt -trustcacerts -destkeystore /opt/payara/appserver/glassfish/domains/domain1/config/cacerts.jks -file /tmp/mycert.crt -alias mycert -srcstorepass storepass -deststorepass ${STORE_PASSWORD} -deststoretype pkcs12

FROM server as production
#COPY --from=build --chown=payara:payara  /app/jee/restApp/target/*.war \
#                  $DEPLOY_DIR
#COPY --from=build --chown=payara:payara /app/jee/wsApp/target/*.war \
#                  $DEPLOY_DIR
#COPY --from=build --chown=payara:payara /app/jee/jsf/target/*.war \
#                  $DEPLOY_DIR
COPY --from=build --chown=payara:payara /app/jee/ear/target/*.ear \
                  $DEPLOY_DIR