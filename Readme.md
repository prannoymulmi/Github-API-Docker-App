# GitHub API viewer
## Introduction 
This Java spring boot (v 2.0.5.RELEASE) rest API application which is containerized
using docker. This application is a simple example for a Java
spring microservice. 

## Application description 
The application provides a rest API interface to search 
users details by the programming language. In the Github API
this cannot be done with a single API request. This API makes
the multiple calls and presents the result.

```
[
  {
    "name": "tests",
    "url": "https://api.github.com/users/test",
    "login": "tesst",
    "avatar_url": "https://avatars3.githubusercontent.com/u/test?v=4"
  },
  {
    "name": "tester tester",
    "url": "https://api.github.com/users/tester",
    "login": "tester",
    "avatar_url": "https://avatars0.githubusercontent.com/u/tester?v=4"
  }
]
```
The result from the end point `http:localhost:8080/search/byLanguage?language=java&page=10`
is presented above. 

### Error handling using AOP
In order to make the code coherent as possible the error handling is executed
using the **Spring AOP**. The classes in the package ` com.example.demo.githubAPI.controllers`
are intercepted using AOP and handled by returning the corresponding ResponseEntity
message. For example if a query searched is not found a 404 status code with
the error message will be returned for all the available controller in the package.
 
### Caching using redis
The application also uses redis to cache the results from the API.
The caching enables an increase in the overall performance when 
requerying. The redis uses Least Recent Used (LRU) algorithm to clear
the cache, in case when the cache storage is full.

The configuration for the redis cache are configured in the file
`redis.conf` which is inside the folder `redis`. 

#### Current configuration for the redis 
* max-memory 2mb
* maxmemory-policy allkeys-lru
* protected-mode no (This is just for development purposes)

The other possible configurations can be checked out using the
link [redis.conf available configurations examples]: 
https://raw.githubusercontent.com/antirez/redis/4.0/redis.conf

 
  
## Instructions
* To start the docker container change the permission for the 
file start.sh. This can be done using the `chmod +x ./start.sh`
inside the terminal. 

* Type `./start.sh` to run the container. It is important that
**docker, docker-compose and maven** are installed in your local machine.

#### Dev tips
* To go inside the container type `docker exec -ti <CONTAINER_NAME> /bin/sh`
* To enable caching during dev add `spring.cache.type=redis` in the `application-dev.properties`
file. This file is located inside the resources. Then before starting the server
start the redis server. This can be done using the command `docker-compose up redis` 
from the root folder. 