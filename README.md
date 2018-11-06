# micronaut-pg-maven-example
Micronaut + PostgreSQL + Maven

1. Run `docker-compose up -d`
1. Run `./mvnw install`
1. Run `java -jar target/pg-example-0.1.jar`

# CRUD
## Get All Users
```
curl http://localhost:7777/users
```
## Get User
```
curl http://localhost:7777/users/1
```
## Create/Update User
```
curl -XPOST http://localhost:7777/users -H "Content-Type: application/json" -d '{"id":1,"username":"Vasya"}'
```
## Delete User
```
curl -XDELETE http://localhost:7777/users/1
```
