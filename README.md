# Project fr.univtln.bruno.samples/jee9.1

Steps to run this project:

1. COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1  docker-compose  up --build -d
2. Visit 
   1. curl http://localhost:8080/jee9.1/resources/sample
   2. curl http://localhost:8080/jee9.1/resources/sample/hello
   3. curl http://localhost:8080/jee9.1/resources/sample/dao1
   4. curl http://localhost:8080/jee9.1/resources/sample/dao2

openssl x509 -outform der -in localhost.pem -out localhost.der
keytool -import -noprompt -trustcacerts -storepass storepass -alias localhost -keystore mycert-pub.jks -file localhost.der

