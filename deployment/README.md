# Deployment

## Local
```sh
docker-compose -f deployment/base.yml -f deployment/develop.yml rm
docker-compose -f deployment/base.yml -f deployment/develop.yml pull
docker-compose -f deployment/base.yml -f deployment/develop.yml build
docker-compose -f deployment/base.yml -f deployment/develop.yml up
```
