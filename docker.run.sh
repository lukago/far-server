#!/usr/bin/env bash

script_name=`basename "$0"`
clearEnv=0
skipTests=0

while getopts "cs" opt; do
  case ${opt} in
    c)
      clearEnv=1
      ;;
    s)
      skipTests=1
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      echo "Usage: $script_name [optional flags: -c; -s;]"
      exit 1
      ;;
  esac
done

if [[ ${clearEnv} == 1 ]]; then
  echo "Clearing docker env..."
  docker stop far-server postgresdb
  docker rm far-server postgresdb
  rm -r ~/.far-db
  mkdir ~/.far-db
fi

if [[ ${skipTests} == 1 ]]; then
  echo "> mvn clean install -U -DskipTests"
  mvn clean install -U -DskipTests
else
  echo "> mvn clean install -U"
  mvn clean install -U
fi

docker build -t far-server etc
docker-compose -f etc/docker-compose.local.yml up -d
