#!/bin/bash

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker run --name countries-catalog -p 5432:5432 -e POSTGRES_PASSWORD=secret -v pgdata:/var/lib/postgresql/data -v ./postgres/script:/docker-entrypoint-initdb.d -e CREATE_DATABASES=countries -e TZ=GMT+3 -e PGTZ=GMT+3 -d postgres:15.1 -c max_prepared_transactions=100
