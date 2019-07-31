#!/bin/bash

function main(){
    echo "> Executa build em todos os projetos"
    for dir in */; do
      echo "> $dir"
      if [[ -e "$dir/Dockerfile" ]]; then
        cd $dir
        ./gradlew clean build docker
        cd ..
      fi
    done
}

main
