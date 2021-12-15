FROM eclipse-temurin:11.0.13_8-jdk-focal

ENV HOME_DIR=/opt/payara
ENV PAYARA_DIR=${HOME_DIR}/appserver \
    SCRIPT_DIR=${HOME_DIR}/scripts \
    CONFIG_DIR=${HOME_DIR}/config \
    DEPLOY_DIR=${HOME_DIR}/deployments \
    PASSWORD_FILE=${HOME_DIR}/passwordFile \
    ADMIN_USER=admin \
    ADMIN_PASSWORD=admin \
    JVM_ARGS="" \
    MEM_MAX_RAM_PERCENTAGE="70.0" \
    MEM_XSS="512k"
ENV PATH="${PATH}:${PAYARA_DIR}/bin"

ARG TINI_VERSION=v0.19.0

# Download tini
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini \
    https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini.asc /

RUN true \
    && apt-get update \
    && apt-get install -y gpg wget unzip\
    && rm -rf /var/lib/apt/lists/* \
    && mkdir -p ${HOME_DIR} \
    && addgroup --gid 1000 payara \
    && adduser --system --uid 1000 --no-create-home --shell /bin/bash --home "${HOME_DIR}" --gecos "" --ingroup payara payara \
    && echo payara:payara | chpasswd \
    && mkdir -p ${PAYARA_DIR} \
    && mkdir -p ${DEPLOY_DIR} \
    && mkdir -p ${CONFIG_DIR} \
    && mkdir -p ${SCRIPT_DIR} \
    && chown -R payara:payara ${HOME_DIR} \
    # Verify tini
    && gpg --verbose --keyserver keyserver.ubuntu.com --recv-keys 595E85A6B1B4779EA4DAAEC70B588DFF0527A9B7 \
    && gpg --verify /tini.asc \
    && chmod +x /tini \
    && true

USER payara
WORKDIR ${HOME_DIR}

# Default payara ports to expose
# 4848: admin console
# 9009: debug port (JPDA)
# 8080: http
# 8181: https
EXPOSE 4848 9009 8080 8181

ENV DOMAIN_NAME=domain1 \
    PAYARA_ARGS="" \
    PREBOOT_COMMANDS=${CONFIG_DIR}/pre-boot-commands.asadmin \
    PREBOOT_COMMANDS_FINAL=${CONFIG_DIR}/pre-boot-commands-final.asadmin \
    POSTBOOT_COMMANDS=${CONFIG_DIR}/post-boot-commands.asadmin \
    POSTBOOT_COMMANDS_FINAL=${CONFIG_DIR}/post-boot-commands-final.asadmin \
    DEPLOY_PROPS=""

ARG PAYARA_VERSION=6.2021.1.Alpha1
ARG PAYARA_SHA1=0262f8af7e52b318109ed43f8a31a1e93f111bbf
ARG PAYARA_PKG=https://search.maven.org/remotecontent?filepath=fish/payara/distributions/payara/${PAYARA_VERSION}/payara-${PAYARA_VERSION}.zip

COPY --chown=payara:payara bin/* ${SCRIPT_DIR}/
#COPY --chown=payara:payara maven/artifacts/payara6 ${PAYARA_DIR}/

# Download and unzip the Payara distribution
RUN ulimit -n 1024 ; wget --no-verbose -O payara.zip ${PAYARA_PKG} && \
    echo "${PAYARA_SHA1} *payara.zip" | sha1sum -c - && \
    unzip -qq payara.zip -d ./ && \
    mv payara*/* ${PAYARA_DIR} && rm -rf payara*

RUN true \
    && echo "AS_ADMIN_PASSWORD=\nAS_ADMIN_NEWPASSWORD=${ADMIN_PASSWORD}" > /tmp/password-change-file.txt \
    && echo "AS_ADMIN_PASSWORD=${ADMIN_PASSWORD}" >> ${PASSWORD_FILE} \
    && ${PAYARA_DIR}/bin/asadmin --user ${ADMIN_USER} --passwordfile=/tmp/password-change-file.txt change-admin-password --domain_name=${DOMAIN_NAME} \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} start-domain ${DOMAIN_NAME} \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} enable-secure-admin \
    && for MEMORY_JVM_OPTION in \
       $(${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} list-jvm-options | grep "Xm[sx]\|Xss"); \
       do\
         ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} delete-jvm-options $MEMORY_JVM_OPTION;\
       done \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} create-jvm-options \
      '-XX\:+UseContainerSupport:-XX\:MaxRAMPercentage=${ENV=MEM_MAX_RAM_PERCENTAGE}:-Xss${ENV=MEM_XSS}' \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} \
      set-log-attributes com.sun.enterprise.server.logging.GFFileHandler.logtoFile=false \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} stop-domain ${DOMAIN_NAME} \
    && rm -rf \
        /tmp/password-change-file.txt \
        ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/osgi-cache \
        ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/logs \
    && true

ENTRYPOINT ["/tini", "--"]
CMD "${SCRIPT_DIR}/entrypoint.sh"
