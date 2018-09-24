#!/usr/bin/env bash
rm -rf target

mvn clean install

sudo docker-compose down

sudo docker-compose up --build

