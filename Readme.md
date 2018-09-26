# GitHub API viewer
This Java spring boot (v 2.0.5.RELEASE) rest API application which is containerized
using docker. This application is a simple example for a Java
spring microservice. 

The application also uses redis to cache the results from the API.
The caching enables an increase in the overall performance when 
requerying.
  
# Instructions
* To start the docker container change the permission for the 
file start.sh. This can be done using the `chmod +x ./start.sh`
inside the terminal. 

* Type `./start.sh` to run the container. It is important that
docker, docker-compose and maven are installed.

# Dev tips
* To go inside the container type `docker exec -ti <CONTAINER_NAME> /bin/sh`
