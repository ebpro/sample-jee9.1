#!/bin/bash
openssl s_client -showcerts -servername localhost -connect localhost:8181 </dev/null | openssl x509 -outform DER > localhost.der
keytool -import -noprompt -trustcacerts -storepass storepass -alias localhost -keystore mycert-pub.jks -file localhost.der
