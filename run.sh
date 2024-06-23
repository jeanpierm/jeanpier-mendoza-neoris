#!/bin/sh
docker compose -p neoris up -d postgres zoo kafka
cd ./ms-customer-domain
mvn clean package
cd ../ms-account-domain
mvn clean package
cd ../
docker compose -p neoris up -d --build
