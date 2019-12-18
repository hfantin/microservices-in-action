#!/bin/sh
echo "********************************************************"
echo "Starting the Eureka Server"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom  \
     -Xms256m        \
     -Xmx1G          \
     -Xshareclasses  \
     -Xquickstart    \
     -jar app.jar
