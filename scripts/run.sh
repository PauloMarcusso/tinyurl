#!/bin/bash

set -e

echo "Running the application..."

cd "$(dirname "$0")/../docker"

docker-compose up --build -d

echo "Waiting for the application to start..."
sleep 10

echo "Verifying health of the application..."
curl -s http://localhost:8080/actuator/health | head -20

echo ""
echo "Application is running!"
echo ""
echo "API: http://localhost:8080"
echo "Health: http://localhost:8080/actuator/health"