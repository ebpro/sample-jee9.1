# Project fr.univtln.bruno.samples/jee9.1

## Steps to run this project

1. COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1  docker-compose  up --build -d
2. Visit 
   1. curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample
   2. curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/hello
   3. curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/main
   4. curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons
   5. curl  -H "Content-Type: application/json" \
      -X POST \
      -d '{"name":"Jeanne"}'  \
      http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons
   6. curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons/843c8236-6c6b-450e-9aa3-211a9b897403
   7. curl -H "Accept: text/xml" http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons
## Import "real" certificate

  openssl x509 -outform der -in localhost.pem -out localhost.der
  keytool -import -noprompt -trustcacerts -storepass storepass -alias localhost -keystore mycert-pub.jks -file localhost.der

