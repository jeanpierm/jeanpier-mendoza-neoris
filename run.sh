#!/bin/sh

# 1. Levanta los servicios de PostgresSQL (DB) y Kafka (Event Streaming Platform) utilizando Docker Compose. Se levantan primero puesto que los microservicios Spring dependen de ellos.
docker compose -p neoris up -d postgres zoo kafka

# 2. Se ubica en la ruta del microservicio de clientes
cd ./ms-customer-domain

# 3. Crea el empaquetado JAR del microservicio de clientes
mvn clean package

# 4. Se ubica en la ruta del microservicio de cuentas y movimientos
cd ../ms-account-domain

# 5. Crea el empaquetado JAR del microservicio de cuentas y movimientos
mvn clean package

# 6. Vuelve a la raiz del proyecto
cd ../

# 7. Levanta los servicios restantes (los microservicios clientes y cuentas) del Compose, creando las imagenes Docker necesarias.
docker compose -p neoris up -d --build
