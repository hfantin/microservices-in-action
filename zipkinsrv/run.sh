#!/bin/sh
java -Xms256m        \
     -Xmx1G          \
     -Xshareclasses  \
     -Xquickstart    \
     -Dspring.profiles.active=$PROFILE                                   \
     -jar app.jar
