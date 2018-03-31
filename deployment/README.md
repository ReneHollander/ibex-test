# Deploying

## On build server
```bash
deployment/build.sh build production 192.168.99.100:5000
deployment/build.sh push production 192.168.99.100:5000
```

## On server
```bash
registry=192.168.99.100:5000 docker-compose -f deployment/production.yml pull
registry=192.168.99.100:5000 docker-compose -f deployment/production.yml up
```
