#!/bin/bash

operation=$1
build_number=$2

function pre_deploy {
    echo "Activating maintenance mode"
    touch maintenance_running

    echo "Shutting down deployment"
    docker-compose -f deployment/base.yml -f deployment/production.yml down

    echo "Creating pre-deploy backup"
    zip -r /mnt/backup/ibex_pre-deploy-backup_${build_number}.zip /var/ibex/
}

function deploy {
    echo "Pulling new images"
    docker-compose -f deployment/base.yml -f deployment/production.yml pull

    echo "Starting deployment"
    docker-compose -f deployment/base.yml -f deployment/production.yml up -d
}

function post_deploy {
    echo "Waiting until application is available"
    while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' localhost:8070/api/cities/enabled)" != "200" ]]; do
        sleep 1
    done

    echo "Deactivating maintenance mode"
    rm maintenance_running
}

case "$operation" in
"pre-deploy")
    pre_deploy
    ;;
"deploy")
    deploy
    ;;
"post-deploy")
    post_deploy
    ;;
*)
    echo "No stage selected"
    exit 1
    ;;
esac
