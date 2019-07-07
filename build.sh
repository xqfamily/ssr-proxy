#!/usr/bin/env bash

function progress() {
    local GREEN CLEAN
    GREEN='\033[0;32m'
    CLEAN='\033[0m'
    printf "\n${GREEN}$@  ${CLEAN}\n" >&2
}

set -e1

# Docker image name
IMAGENAME=ssr
DOCKER_TAG=$(date -u "+%Y%m%d-%H%M%S");

progress "Building jar file ..."
mvn package -Dmaven.test.skip=true

progress "Building docker image ... "
docker build -t ${IMAGENAME}:${DOCKER_TAG} .

progress "run ......"
docker run --name ssr -p 8080:8080 -d ${IMAGENAME}:${DOCKER_TAG}

#progress "delete images ......"
#docker rmi ${IMAGENAME}:${DOCKER_TAG}



