#!/bin/bash

if [ -z ${2+x} ]; then
    profile="latest"
else
    profile=${2}
fi

if [ -z ${3+x} ]; then
    registry=""
else
    registry=${3}/
fi

function get_name {
    local img=$1
    echo "${registry}ibex/$img:$profile"
}

function build {
    local img=$1
    local name=$(get_name $1)
    echo "Building $1 image: $name"
    docker build --pull --tag $name -f deployment/$img/Dockerfile .
}

function push {
    local img=$1
    local name=$(get_name $1)
    echo "Pushing $1 image: $name"
    docker push $name
}

case "$1" in
"build")
    build backend
    build frontend
    ;;
"push" | "3")
    push backend
    push frontend
    ;;
*)
    echo "Please specify what to do: build, push"
    ;;
esac
