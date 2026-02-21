#!/bin/bash
set -e

echo "Running development server..."

cd "$(dirname "$0")/../docker"

docker-compose -f docker-compose.dev.yml up -d

echo "Waiting for the Postgre to start..."
sleep 5

echo "Environment is ready!"
echo ""
echo "Admin panel: http://localhost:8081"
echo "To run the application"
echo "./mvnw spring-boot:run -Dspring-boot.run.profiles=local"