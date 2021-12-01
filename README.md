# Project fr.univtln.bruno.samples/jee9.1

## Steps to run this project

1. ```COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1  docker-compose  up --build -d```
2. Visit 
   1. ```curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample```
   2. ```curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/hello```
   3. ```curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/main```
   4. ```curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons```
   5. ```
      curl  -H "Content-Type: application/json" \
      -X POST \
      -d '{"name":"Jeanne"}'  \
      http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons
   6. ```
      curl http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons/843c8236-6c6b-450e-9aa3-211a9b897403```
   7. ```curl -H "Accept: text/xml" http://localhost:8080/restApp-1.0-SNAPSHOT/resources/sample/persons```

   
## Use a "real" certificate
Récupérer le certificat du serveur
```
echo quit | openssl s_client \
            -showcerts -servername localhost -connect localhost:8181 >! localhost.pem
```

Convertir en keystore java
```
keytool -noprompt -storepass storepass -import \
        -trustcacerts -alias mycert -file localhost.pem -keystore mycert-pub.jks
```

Il est possible d'utiliser le certificat avec curl
```
curl --cacert localhost.pem  \
   https://localhost:8181/restApp-1.0-SNAPSHOT/resources/sample/persons/843c8236-6c6b-450e-9aa3-211a9b897403
```

Avec un reverse proxy :
```
echo quit | openssl s_client -showcerts \
            -servername localhost -connect localhost:8888 >! localhost.pem

curl --cacert localhost.pem  \
      https://localhost:8181/restApp-1.0-SNAPSHOT/resources/sample/persons/843c8236-6c6b-450e-9aa3-211a9b897403
      
keytool -noprompt -storepass storepass -import \
        -trustcacerts -alias mycert -file localhost.pem -keystore mycert-pub.jks      
```






















