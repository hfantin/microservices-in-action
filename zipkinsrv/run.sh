#!/bin/sh
java  -Dspring.profiles.active=$PROFILE                                   \
     -jar app.jar
