# Personal Finance Backend
A Spring Boot RESTful API to be used in conjunction with the Personal Finance Frontend Angular web app.

### Docker
Build image:
```
./mvnw install dockerfile:build
```

Push image to Docker Hub:
```
./mvnw dockerfile:push
```

Run container on host port 8080 and provide host env variable:
```
docker run -p 8080:8080 -e PERFI_PROD_DB_PWD akosborn/personal-finance-server
```