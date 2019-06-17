#!/usr/bin/env bash

docker-compose up --build -d;

# wait for mysql to start
echo "wait for 15 seconds..."
sleep 15;

docker exec -i slick-dev-box_mysql-db_1 mysql -u root -p123123  -e 'CREATE DATABASE slick';
